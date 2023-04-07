package tqs.hw1.envmonitor.cache;

public class CacheItem<V> {
    private final Long ts;
    private final V value;

    public CacheItem(V value) {
        this.ts = System.currentTimeMillis();
        this.value = value;
    }

    public Long getTs() {
        return ts;
    }

    public V getValue() {
        return value;
    }
}
