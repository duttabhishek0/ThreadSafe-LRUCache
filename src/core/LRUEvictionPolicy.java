package core;

import Interface.EvictionPolicy;
import domain.AccessStatistics;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class LRUEvictionPolicy<K> implements EvictionPolicy<K> {

    @Override
    public boolean shouldEvict(int cacheSize, Duration ttl, Map<K, AccessStatistics<K>> statistics) {
        if (cacheSize <= 0) {
            throw new IllegalArgumentException("Cache size should be greater than 0.");
        }

        if (ttl.isNegative() || ttl.isZero()) {
            return false;
        }

        long currentTimestamp = Instant.now().getEpochSecond();

        Optional<Map.Entry<K, AccessStatistics<K>>> leastAccessedEntry = (Optional<Map.Entry<K, AccessStatistics<K>>>) statistics.entrySet().stream()
                .min(Comparator.comparingLong(entry -> entry.getValue().getLastAccessTimestamp()));

        if (leastAccessedEntry.isPresent()) {
            AccessStatistics<K> leastAccessedStatistics = leastAccessedEntry.get().getValue();
            long timeSinceLastAccess = Duration.between(Instant.ofEpochSecond(leastAccessedStatistics.getLastAccessTimestamp()), Instant.ofEpochSecond(currentTimestamp)).getSeconds();
            if (timeSinceLastAccess >= ttl.getSeconds()) {
                return true;
            }
        }

        return false;
    }

}
