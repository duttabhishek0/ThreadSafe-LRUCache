package test;

import core.LRUCache;
import org.junit.Test;

import java.time.Duration;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LRUCacheTest {

    @Test
    public void testTTL() throws InterruptedException {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        cache.put(1, "one", Duration.ofSeconds(1));
        Thread.sleep(2000);
        assertNull(cache.get(1));
    }

    @Test
    public void testNegativeTTL() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        assertThrows(IllegalArgumentException.class, () -> cache.put(1, "1", Duration.ofSeconds(-10)));
    }

    @Test
    public void testNonExistentKey() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        assertNull(cache.get(1));
    }
}
