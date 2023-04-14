package domain;

import java.time.Duration;

public class CacheEntry<V> {
    private final V value;
    private final long expiryTime;

    public CacheEntry(V value, Duration ttl) {
        this.value = value;
        this.expiryTime = System.currentTimeMillis() + ttl.toMillis();
    }

    public V getValue() {
        return value;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}
