package io.jhpark.kopic.lobby.matchmaking.infra;

import io.jhpark.kopic.lobby.engine.infra.DummySampleLobbyStateStore;
import io.jhpark.kopic.lobby.matchmaking.app.RandomRoomIndex;
import io.jhpark.kopic.lobby.matchmaking.domain.QuickJoinCandidate;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Dummy sample random room index reader.
 * Replace this class with a real Redis-backed implementation later.
 */
@Component
public class DummySampleRandomRoomIndex implements RandomRoomIndex {

    private final DummySampleLobbyStateStore stateStore;

    public DummySampleRandomRoomIndex(DummySampleLobbyStateStore stateStore) {
        this.stateStore = stateStore;
    }

    @Override
    public List<QuickJoinCandidate> findJoinableCandidates(int limit) {
        return stateStore.findJoinableRandomRoomIds(limit).stream()
                .map(QuickJoinCandidate::new)
                .toList();
    }
}
