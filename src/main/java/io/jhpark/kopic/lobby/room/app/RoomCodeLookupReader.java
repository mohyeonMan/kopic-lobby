package io.jhpark.kopic.lobby.room.app;

import java.util.Optional;

public interface RoomCodeLookupReader {

    Optional<String> findRoomIdByCode(String roomCode);
}
