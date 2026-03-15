package io.jhpark.kopic.lobby.engine.infra;

import io.jhpark.kopic.lobby.engine.app.EnginePresenceReader;
import io.jhpark.kopic.lobby.engine.domain.EnginePresence;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Dummy sample engine presence reader.
 * Replace this class with a real Redis-backed implementation later.
 */
@Component
public class DummySampleEnginePresenceReader implements EnginePresenceReader {

    private final DummySampleLobbyStateStore stateStore;

    public DummySampleEnginePresenceReader(DummySampleLobbyStateStore stateStore) {
        this.stateStore = stateStore;
    }

    @Override
    public List<EnginePresence> findActiveCandidates() {
        return stateStore.findActivePresences();
    }

    @Override
    public Optional<EnginePresence> findById(String engineId) {
        return stateStore.findActivePresences().stream()
                .filter(presence -> presence.engineId().equals(engineId))
                .findFirst();
    }
}
