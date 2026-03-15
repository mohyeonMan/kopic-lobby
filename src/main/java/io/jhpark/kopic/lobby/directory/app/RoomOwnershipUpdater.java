package io.jhpark.kopic.lobby.directory.app;

import io.jhpark.kopic.lobby.directory.domain.RoomOwnership;

public interface RoomOwnershipUpdater {

    void update(RoomOwnership roomOwnership);
}
