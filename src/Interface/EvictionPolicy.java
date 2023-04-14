package Interface;

import domain.AccessStatistics;

import java.time.Duration;
import java.util.Map;

public interface EvictionPolicy<K>{
    boolean shouldEvict(int cacheSize, Duration ttl, Map<K, AccessStatistics<K>> statistics);
}
