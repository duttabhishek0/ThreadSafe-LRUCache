package domain;

import java.time.Duration;
import java.time.Instant;

/**
 * A node in the doubly linked list used for LRU eviction in the cache.
 *
 * @param <K> the type of the key in the cache
 * @param <V> the type of the value in the cache
 */
public class CacheNode<K, V> {

    private K key;
    private V value;
    private CacheNode<K, V> prev;
    private CacheNode<K, V> next;
    private Duration ttl;
    private final Instant expirationTime;

    /**
     * Constructs a new CacheNode with the given key and value.
     *
     * @param key   the key of the node in the cache
     * @param value the value of the node in the cache
     */
    public CacheNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.prev = null;
        this.next = null;
        this.ttl = Duration.ofSeconds(Integer.MAX_VALUE);
        this.expirationTime = Instant.now().plus(ttl);
    }
    /**
     * Constructs a new CacheNode with the given key and value.
     *
     * @param key   the key of the node in the cache
     * @param value the value of the node in the cache
     * @param ttl   the time-to-live duration of the entry in the cache
     */
    public CacheNode(K key, V value, Duration ttl) {
        this.key = key;
        this.value = value;
        this.prev = null;
        this.next = null;
        this.ttl = ttl != null ? ttl : Duration.ofSeconds(Long.MAX_VALUE);
        this.expirationTime = Instant.now().plus(ttl);
    }

    /**
     * Returns the key of the node in the cache.
     *
     * @return the key of the node in the cache
     */
    public K getKey() {
        return key;
    }

    /**
     * Returns the value of the node in the cache.
     *
     * @return the value of the node in the cache
     */
    public V getValue() {
        return value;
    }

    /**
     * Returns the previous node in the doubly linked list.
     *
     * @return the previous node in the doubly linked list
     */
    public CacheNode<K, V> getPrev() {
        return prev;
    }

    /**
     * Returns the time-to-live (TTL) duration of the entry in the cache.
     *
     * @return the time-to-live (TTL) duration of the entry in the cache
     */
    public Duration getTTL() {
        return ttl;
    }

    /**
     * Returns the expiration time of the entry in the cache.
     *
     * @return the expiration time of the entry in the cache
     */
    public Instant getExpirationTime() {
        return expirationTime;
    }
    /**
     * Sets the previous node in the doubly linked list.
     *
     * @param prev the previous node in the doubly linked list
     */
    public void setPrev(CacheNode<K, V> prev) {
        this.prev = prev;
    }

    /**
     * Returns the next node in the doubly linked list.
     *
     * @return the next node in the doubly linked list
     */
    public CacheNode<K, V> getNext() {
        return next;
    }

    /**
     * Sets the next node in the doubly linked list.
     *
     * @param next the next node in the doubly linked list
     */
    public void setNext(CacheNode<K, V> next){
        this.next = next;
    }

    /**
     * Sets the value of the node in the cache.
     *
     * @param value the next node in the doubly linked list
     */
    public void setValue(V value){
        this.value = value;
    }
    /**
     * Sets the value of the ttl in the cache.
     *
     * @param ttl the next node in the doubly linked list
     */
    public void setTtl(Duration ttl){
        this.ttl = ttl;
    }

    /**
     * Checks if the entry in the cache is expired.
     *
     * @return true if the entry in the cache is expired, false otherwise
     */
    public boolean isExpired() {
        return Instant.now().isAfter(expirationTime);
    }

    /**
     * Returns a string representation of the entry.
     *
     * @return a string representation of the entry
     */
    @Override
    public String toString() {
        return "CacheEntry{" +
                "key=" + key +
                ", value=" + value +
                ", expirationTime=" + expirationTime +
                '}';
    }
}
