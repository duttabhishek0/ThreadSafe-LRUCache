package core;

import domain.CacheNode;
import Interface.Cache;
import utils.Utility;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * A cache that uses a hash map for fast get and put operations, and a doubly linked list for LRU eviction.
 *
 * @param <K> the type of the key in the cache
 * @param <V> the type of the value in the cache
 */
public class LRUCache<K, V> implements Cache<K, V> {

    private final Map<K, CacheNode<K,V>> cacheMap;
    private final int maxSize;
    private CacheNode<K,V> head;
    private CacheNode<K,V> tail;
    private Utility<K, V> utility;

    /**
     * Constructs a new Core.LRUCache with the given capacity and time-to-live (TTL) duration.
     *
     * @param capacity the maximum number of entries the cache can hold
     */
    public LRUCache(int capacity) {
        this.maxSize = capacity;
        this.cacheMap = new HashMap<>(maxSize);
        utility = new Utility<>();
        this.head = null;
        this.tail = null;
    }


    /**
     * Adds or updates the entry for the given key with the given value in the cache.
     * If the cache is at capacity, the least recently used entry is evicted.
     *
     * @param key   the key of the entry to add or update
     * @param value the value of the entry to add or update
     */
    @Override
    public synchronized void put(K key, V value) {
        if(cacheMap.containsKey(key)) {
            CacheNode<K, V> node = cacheMap.get(key);
            node.setValue(value);
            utility.moveToHead(node, head, tail);
        }
        else{
            CacheNode<K, V> node = new CacheNode<>(key, value);
            cacheMap.put(key, node);
            utility.addNode(node, head, tail);
            if(cacheMap.size() > maxSize) {
                removeTail();
            }
        }
    }

    /**
     * Adds or updates the entry for the given key with the given value in the cache.
     * If the cache is at capacity, the least recently used entry is evicted.
     *
     * @param key   the key of the entry to add or update
     * @param value the value of the entry to add or update
     * @param ttl   the time-to-live duration of each entry in the cache
     */
    @Override
    public synchronized void put(K key, V value, Duration ttl) {
        if (ttl.isNegative()) {
            throw new IllegalArgumentException("TTL value cannot be negative");
        }
        if(cacheMap.containsKey(key)) {
            CacheNode<K, V> node = cacheMap.get(key);
            node.setValue(value);
            node.setTtl(ttl);
            utility.moveToHead(node, head, tail);
        }
        else{
            CacheNode<K, V> node = new CacheNode<>(key, value, ttl);
            cacheMap.put(key, node);
            utility.addNode(node, head, tail);

            if(cacheMap.size() > maxSize) {
                removeTail();
            }
        }
    }

    /**
     * Returns the value associated with the given key in the cache, or null if the key is not found.
     * If the entry for the key is expired, it is removed from the cache and null is returned.
     * Otherwise, the entry is moved to the front of the LRU list.
     *
     * @param key the key of the entry to look up
     * @return the value associated with the key, or null if the key is not found
     */
    @Override
    public synchronized V get(K key) {
        CacheNode<K, V> node = cacheMap.get(key);
        if(node == null){
            return null;
        }
        if(node.isExpired()) {
            cacheMap.remove(key);
            return null;
        }
        utility.moveToHead(node, head, tail);
        return node.getValue();
    }

    /**
     * Removes the entry for the given key from the cache, if it exists.
     *
     * @param key the key of the entry to remove
     */
    @Override
    public synchronized V remove(K key) {
        CacheNode<K, V> node = cacheMap.get(key);
        if (node != null) {
            V val = node.getValue();
            utility.removeFromList(node, head, tail);
            return val;
        }
        return null;
    }
    /**
     * Removes the least recently used entry from the cache.
     */
    private void removeTail() {
        cacheMap.remove(tail.getKey());
        if (tail == head) {
            head = null;
            tail = null;
        } else {
            tail = tail.getPrev();
            tail.setNext(null);
        }
    }

    /**
     * Returns the number of entries in the cache.
     *
     * @return the number of entries in the cache
     */
    public synchronized int size() {
        return cacheMap.size();
    }

    /**
     * Clears all entries from the cache.
     */
    public synchronized void clear() {
        cacheMap.clear();
        head = null;
        tail = null;
    }

    @Override
    public CacheMonitor.CacheStats getStats() {
        return null;
    }


}
