package io.jhpark.kopic.lobby.directory.app;

import io.jhpark.kopic.lobby.directory.domain.RoomOwnership;
import java.util.Optional;

public interface RoomRoutingReader {

    Optional<RoomOwnership> findByRoomId(String roomId);
}
