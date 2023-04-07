package tqs.hw1.envmonitor.cache;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public abstract class Cache<K, V> {
    private final Long ttl;
    private final Integer capacity;
    private Integer nRequests;
    private Integer nHits;
    private Integer nMisses;
    private final Map<K, CacheItem<V>> cache;

    public Cache(Long ttl, Integer capacity) {
        this.ttl = ttl;
        this.capacity = capacity;
        this.nRequests = 0;
        this.nHits = 0;
        this.nMisses = 0;
        this.cache = new HashMap<>();
    }

    public V get(K key) {
        V res = null;
        nRequests++;
        if (cache.containsKey(key)) {
            CacheItem<V> item = cache.get(key);
            if (item.getTs() + ttl > System.currentTimeMillis()) {
                nHits++;
                res = item.getValue();
            } else {
                nMisses++;
                cache.remove(key);
            }
        }
        else {
            nMisses++;
        }
        return res;
    }

    public void put(K key, V value) {
        // Clear expired items
        cache.entrySet().removeIf(entry -> entry.getValue().getTs() + ttl <= System.currentTimeMillis());
        // Clear oldest item if cache is full
        if (!cache.containsKey(key) && cache.size() == capacity) {
            cache.entrySet().stream()
                    .min(Comparator.comparing(entry -> entry.getValue().getTs()))
                    .ifPresent(entry -> cache.remove(entry.getKey()));
        }
        cache.put(key, new CacheItem<>(value));
    }

    public Long getTtl() {
        return ttl;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getNRequests() {
        return nRequests;
    }

    public Integer getNHits() {
        return nHits;
    }

    public Integer getNMisses() {
        return nMisses;
    }

    public Integer getNItems() {
        return cache.size();
    }
}
