package io.jhpark.kopic.lobby.room.app;

import io.jhpark.kopic.lobby.room.domain.RoomEntry;
import java.util.Optional;

public interface RoomEntryReader {

    Optional<RoomEntry> findByRoomId(String roomId);
}
