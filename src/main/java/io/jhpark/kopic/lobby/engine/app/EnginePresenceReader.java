package io.jhpark.kopic.lobby.engine.app;

import io.jhpark.kopic.lobby.engine.domain.EnginePresence;
import java.util.List;
import java.util.Optional;

public interface EnginePresenceReader {

    List<EnginePresence> findActiveCandidates();

    Optional<EnginePresence> findById(String engineId);
}
