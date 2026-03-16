package io.jhpark.kopic.lobby.allocation.app;

import io.jhpark.kopic.lobby.allocation.domain.EngineCandidate;
import io.jhpark.kopic.lobby.allocation.domain.EngineSelectionPolicy;
import io.jhpark.kopic.lobby.engine.app.EnginePresenceReader;
import io.jhpark.kopic.lobby.engine.domain.EnginePresence;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class EngineAllocationService {

    private final EnginePresenceReader enginePresenceReader;
    private final EngineSelectionPolicy engineSelectionPolicy;

    public EngineAllocationService(
            EnginePresenceReader enginePresenceReader,
            EngineSelectionPolicy engineSelectionPolicy
    ) {
        this.enginePresenceReader = enginePresenceReader;
        this.engineSelectionPolicy = engineSelectionPolicy;
    }

    public String allocateOwnerEngineId() {
        return allocateOwnerEngine().engineId();
    }

    public String allocateOwnerEngineIdExcluding(String excludedEngineId) {
        return allocateOwnerEngineExcluding(excludedEngineId).engineId();
    }

    public EnginePresence allocateOwnerEngine() {
        return allocateOwnerEngineExcluding(null);
    }

    public EnginePresence allocateOwnerEngineExcluding(String excludedEngineId) {
        return selectPresence(excludedEngineId);
    }

    private EnginePresence selectPresence(String excludedEngineId) {
        List<EngineCandidate> candidates = enginePresenceReader.findActiveCandidates().stream()
                .filter(presence -> excludedEngineId == null || !presence.engineId().equals(excludedEngineId))
                .map(EngineCandidate::new)
                .toList();

        return engineSelectionPolicy.select(candidates)
                .map(EngineCandidate::presence)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.SERVICE_UNAVAILABLE,
                        "no available engine"
                ));
    }
}
