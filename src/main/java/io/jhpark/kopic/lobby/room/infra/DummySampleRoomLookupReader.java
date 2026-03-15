package io.jhpark.kopic.lobby.room.infra;

import io.jhpark.kopic.lobby.engine.infra.DummySampleLobbyStateStore;
import io.jhpark.kopic.lobby.room.app.RoomCodeLookupReader;
import io.jhpark.kopic.lobby.room.app.RoomEntryReader;
import io.jhpark.kopic.lobby.room.domain.RoomEntry;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Dummy sample room lookup reader.
 * Replace this class with a real Redis-backed implementation later.
 */
@Component
public class DummySampleRoomLookupReader implements RoomCodeLookupReader, RoomEntryReader {

    private final DummySampleLobbyStateStore stateStore;

    public DummySampleRoomLookupReader(DummySampleLobbyStateStore stateStore) {
        this.stateStore = stateStore;
    }

    @Override
    public Optional<String> findRoomIdByCode(String roomCode) {
        return stateStore.findRoomIdByCode(roomCode);
    }

    @Override
    public Optional<RoomEntry> findByRoomId(String roomId) {
        return stateStore.findRoomEntry(roomId);
    }
}
