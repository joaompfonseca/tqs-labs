package tqs.hw1.envmonitor.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CacheTest {

    static class IntegerCache extends Cache<String, Integer> {
        public IntegerCache(Long ttl, Integer capacity) {
            super(ttl, capacity);
        }
    }

    private IntegerCache cache;
    private final Long ttl = 1000L;
    private final Integer capacity = 5;

    @BeforeEach
    void setUp() {
        cache = new IntegerCache(ttl, capacity);
    }

    void assertStats(Integer nRequests, Integer nHits, Integer nMisses, Integer nItems) {
        assertThat(cache.getNRequests()).isEqualTo(nRequests);
        assertThat(cache.getNHits()).isEqualTo(nHits);
        assertThat(cache.getNMisses()).isEqualTo(nMisses);
        assertThat(cache.getNItems()).isEqualTo(nItems);
    }

    @Test
    void givenNewCache_thenInitialState() {
        assertThat(cache.getTtl()).isEqualTo(ttl);
        assertThat(cache.getCapacity()).isEqualTo(capacity);
        assertStats(0, 0, 0, 0);
    }

    @Test
    void givenEmptyCache_whenGet_thenMiss() {
        assertThat(cache.getNItems()).isEqualTo(0);

        Integer value = cache.get("key");

        assertThat(value).isNull();
        assertStats(1, 0, 1, 0);
    }

    @Test
    void givenEmptyCache_whenPut_thenHit() {
        assertThat(cache.getNItems()).isEqualTo(0);

        cache.put("key", 100);

        assertThat(cache.get("key")).isEqualTo(100);
        assertStats(1, 1, 0, 1);
    }

    @Test
    void givenCacheWithNonExpiredItem_whenGet_thenHit() {
        cache.put("key", 100);

        Integer value = cache.get("key");

        assertThat(value).isEqualTo(100);
        assertStats(1, 1, 0, 1);
    }

    @Test
    void givenCacheWithExpiredItem_whenGet_thenMiss() throws InterruptedException {
        cache.put("key", 100);
        Thread.sleep(ttl + 1L);

        Integer value = cache.get("key");

        assertThat(value).isNull();
        assertStats(1, 0, 1, 0);
    }

    @Test
    void givenCacheWithItem_whenPut_thenReplace() {
        cache.put("key", 100);

        cache.put("key", 200);

        assertThat(cache.get("key")).isEqualTo(200);
        assertStats(1, 1, 0, 1);
    }

    @Test
    void givenCacheWithExpiredItem_whenPutDifferent_thenClearExpired() throws InterruptedException {
        cache.put("key1", 100);
        Thread.sleep(ttl + 1L);

        cache.put("key2", 200);

        assertThat(cache.get("key1")).isNull();
        assertThat(cache.get("key2")).isEqualTo(200);
        assertStats(2, 1, 1, 1);
    }

    @Test
    void givenFullCache_whenPutDifferent_thenOldestDeleted() {
        for (int i = 1; i <= capacity; i++) {
            cache.put("key" + i, i * 100);
        }
        assertStats(0, 0, 0, capacity);

        cache.put("key", 999);

        assertThat(cache.get("key1")).isNull();
        for (int i = 2; i <= capacity; i++) {
            assertThat(cache.get("key" + i)).isEqualTo(i * 100);
        }
        assertThat(cache.get("key")).isEqualTo(999);
        assertStats(capacity + 1, capacity, 1, capacity);
    }
}
