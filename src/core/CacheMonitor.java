package core;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class CacheMonitor<K> {
    private final AtomicInteger hits;
    private final AtomicInteger misses;
    private final AtomicInteger evictions;
    private final AtomicInteger size;

    public CacheMonitor(Duration ttl) {
        hits = new AtomicInteger(0);
        misses = new AtomicInteger(0);
        evictions = new AtomicInteger(0);
        size = new AtomicInteger(0);
    }

    public void onHit(K key) {
        hits.incrementAndGet();
    }

    public void onMiss(K key) {
        misses.incrementAndGet();
    }

    public void onEviction(K key) {
        evictions.incrementAndGet();
        size.decrementAndGet();
    }

    public void onPut(K key) {
        size.incrementAndGet();
    }

    public CacheStats getStats() {
        return new CacheStats(hits.get(), misses.get(), evictions.get(), size.get());
    }

    public void onCacheHit() {

    }

    public static class CacheStats {
        private final int hits;
        private final int misses;
        private final int evictions;
        private final int size;

        public CacheStats(int hits, int misses, int evictions, int size) {
            this.hits = hits;
            this.misses = misses;
            this.evictions = evictions;
            this.size = size;
        }

        public int getHits() {
            return hits;
        }

        public int getMisses() {
            return misses;
        }

        public int getEvictions() {
            return evictions;
        }

        public int getSize() {
            return size;
        }
    }
}
