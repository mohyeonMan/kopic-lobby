package io.jhpark.kopic.lobby.directory.infra;

import io.jhpark.kopic.lobby.directory.app.RoomOwnershipUpdater;
import io.jhpark.kopic.lobby.directory.app.RoomRoutingReader;
import io.jhpark.kopic.lobby.directory.domain.RoomOwnership;
import io.jhpark.kopic.lobby.engine.infra.DummySampleLobbyStateStore;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Dummy sample routing hint adapter.
 * Replace this class with a real Redis-backed implementation later.
 */
@Component
public class DummySampleRoomRoutingReader implements RoomRoutingReader, RoomOwnershipUpdater {

    private final DummySampleLobbyStateStore stateStore;

    public DummySampleRoomRoutingReader(DummySampleLobbyStateStore stateStore) {
        this.stateStore = stateStore;
    }

    @Override
    public Optional<RoomOwnership> findByRoomId(String roomId) {
        return stateStore.findOwnerEngineId(roomId)
                .map(ownerEngineId -> new RoomOwnership(roomId, ownerEngineId));
    }

    @Override
    public void update(RoomOwnership roomOwnership) {
        stateStore.updateOwner(roomOwnership.roomId(), roomOwnership.ownerEngineId());
    }
}
