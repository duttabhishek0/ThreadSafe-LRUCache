package Interface;

import core.CacheMonitor;

import java.time.Duration;

public interface Cache<K, V> {
    /**
     * Associates the specified value with the specified key in this cache.
     * If the cache previously contained a mapping for the key, the old value
     * is replaced by the specified value.
     *
     * @param key the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     */
    void put(K key, V value);


    /**
     * Associates the specified value with the specified key in this cache.
     * If the cache previously contained a mapping for the key, the old value
     * is replaced by the specified value.
     *
     * @param key the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @param ttl   the time-to-live duration of each entry in the cache
     */
    void put(K key, V value, Duration ttl);

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * cache contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if this
     * cache contains no mapping for the key
     */
    V get(K key);

    /**
     * Removes the mapping for a key from this cache if it is present.
     *
     * @param key the key whose mapping is to be removed from the cache
     * @return the previous value associated with the key, or null if there was
     * no mapping for the key
     */
    V remove(K key);

    /**
     * Removes all the mappings from this cache. The cache will be empty after
     * this call returns.
     */
    void clear();

    /**
     * Returns statistics about the cache's current state.
     *
     * @return a CacheStats object containing statistics about the cache
     */
    CacheMonitor.CacheStats getStats();
}
