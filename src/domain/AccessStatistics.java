package domain;

import java.time.Duration;
import java.time.Instant;

public class AccessStatistics<K> {
    private int accessCount;
    private Instant lastAccessTime;
    private int hitCount;
    private int missCount;

    public AccessStatistics() {
        this.accessCount = 0;
        this.lastAccessTime = Instant.now();
        hitCount = 0;
        missCount = 0;
    }

    public void incrementAccessCount() {
        this.accessCount++;
        this.lastAccessTime = Instant.now();
    }

    public int getAccessCount() {
        return accessCount;
    }
    public int getHits() {
        return hitCount;
    }

    public int getMiss() {
        return missCount;
    }

    public long getLastAccessTimestamp() {
        return lastAccessTime.toEpochMilli();
    }

    public void incrementHitCount() {
        hitCount++;
        lastAccessTime = Instant.now();
    }

    public void incrementMissCount() {
        missCount++;
        lastAccessTime = Instant.now();
    }

    public void reset() {
        lastAccessTime = Instant.now();
        hitCount = 0;
        missCount = 0;
    }

    public void updateAccess() {
        lastAccessTime = Instant.now();
    }
}
