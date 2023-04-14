package core;

import Interface.Cache;
import domain.AccessStatistics;

import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFUCache<K, V> implements Cache<K, V> {
    private final int capacity;
    private final Map<K, V> cache;
    private final Map<K, AccessStatistics> accessStatistics;
    private final Map<Integer, LinkedHashSet<K>> frequencyLists;
    private int minFrequency;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();
        accessStatistics = new HashMap<>();
        frequencyLists = new HashMap<>();
        minFrequency = 0;
        frequencyLists.put(1, new LinkedHashSet<>());
    }

    @Override
    public void put(K key, V value) {
        if (capacity == 0) return;

        if (cache.containsKey(key)) {
            cache.put(key, value);
            accessStatistics.get(key).updateAccess();
            return;
        }

        if (cache.size() == capacity) {
            evict();
        }

        cache.put(key, value);
        AccessStatistics stats = new AccessStatistics();
        accessStatistics.put(key, stats);
        frequencyLists.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
        minFrequency = 1;
    }

    @Override
    public void put(K key, V value, Duration ttl) {

    }

    @Override
    public V get(K key) {
        if (!cache.containsKey(key)) {
            return null;
        }

        AccessStatistics stats = accessStatistics.get(key);
        stats.incrementAccessCount();
        int frequency = stats.getAccessCount();

        LinkedHashSet<K> oldList = frequencyLists.get(frequency);
        LinkedHashSet<K> newList = frequencyLists.computeIfAbsent(frequency + 1, k -> new LinkedHashSet<>());



        if (oldList.isEmpty() && frequency == minFrequency) {
            minFrequency = frequency + 1;
        }

        return cache.get(key);
    }

    @Override
    public V remove(K key) {
        return null;
    }

    private void evict() {
        LinkedHashSet<K> minFreqList = frequencyLists.get(minFrequency);
        K evictKey = minFreqList.iterator().next();
        minFreqList.remove(evictKey);

        if (minFreqList.isEmpty()) {
            frequencyLists.remove(minFrequency);
            minFrequency++;
        }

        cache.remove(evictKey);
        accessStatistics.remove(evictKey);
    }

    @Override
    public void clear() {
        cache.clear();
        accessStatistics.clear();
        frequencyLists.clear();
        frequencyLists.put(1, new LinkedHashSet<>());
        minFrequency = 0;
    }

    public int size() {
        return cache.size();
    }

    @Override
    public CacheMonitor.CacheStats getStats() {
        int hits = 0;
        int misses = 0;
        int evictions = 0;
        for (AccessStatistics stats : accessStatistics.values()) {
            if (stats.getHits() > 0) {
                hits++;
            } else {
                misses++;
            }
        }
        return new CacheMonitor.CacheStats(hits, misses, evictions, size());
    }
}
