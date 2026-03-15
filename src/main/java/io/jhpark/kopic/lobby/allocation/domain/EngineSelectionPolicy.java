package io.jhpark.kopic.lobby.allocation.domain;

import java.util.List;
import java.util.Optional;

public interface EngineSelectionPolicy {

    Optional<EngineCandidate> select(List<EngineCandidate> candidates);
}
