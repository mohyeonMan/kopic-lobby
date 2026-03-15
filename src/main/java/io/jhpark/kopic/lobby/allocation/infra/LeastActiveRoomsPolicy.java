package io.jhpark.kopic.lobby.allocation.infra;

import io.jhpark.kopic.lobby.allocation.domain.EngineCandidate;
import io.jhpark.kopic.lobby.allocation.domain.EngineSelectionPolicy;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class LeastActiveRoomsPolicy implements EngineSelectionPolicy {

    @Override
    public Optional<EngineCandidate> select(List<EngineCandidate> candidates) {
        return candidates.stream()
                .min(Comparator.comparingInt(candidate -> candidate.presence().activeRooms()));
    }
}
