package io.jhpark.kopic.lobby.allocation.domain;

import io.jhpark.kopic.lobby.engine.domain.EnginePresence;

public record EngineCandidate(
        EnginePresence presence
) {
}
