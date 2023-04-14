package core;

import Interface.EvictionPolicy;
import domain.AccessStatistics;

import java.time.Duration;
import java.util.Map;

public class LFUEvictionPolicy<K> implements EvictionPolicy<K> {
    @Override
    public boolean shouldEvict(int cacheSize, Duration ttl, Map<K, AccessStatistics<K>> statistics) {
        int minFrequency = Integer.MAX_VALUE;
        K keyToRemove = null;
        for (Map.Entry<K, AccessStatistics<K>> entry : statistics.entrySet()) {
            AccessStatistics accessStatistics = entry.getValue();
            if (accessStatistics.getAccessCount() < minFrequency) {
                minFrequency = accessStatistics.getAccessCount();
                keyToRemove = entry.getKey();
            }
        }
        if (keyToRemove != null) {
            statistics.remove(keyToRemove);
            return true;
        }
        return false;
    }

}
