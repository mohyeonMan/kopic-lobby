package io.jhpark.kopic.lobby.migration.infra;

import io.jhpark.kopic.lobby.migration.domain.MigrationStatus;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

/**
 * Dummy sample migration state store.
 * Replace this class with a real persistence implementation later.
 */
@Component
public class DummySampleMigrationStateStore {

    private final Map<String, MigrationStatus> statuses = new ConcurrentHashMap<>();

    public void save(MigrationStatus status) {
        statuses.put(status.roomId(), status);
    }

    public Optional<MigrationStatus> findByRoomId(String roomId) {
        return Optional.ofNullable(statuses.get(roomId));
    }
}
