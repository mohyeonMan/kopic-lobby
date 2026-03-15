package io.jhpark.kopic.lobby.room.app;

import io.jhpark.kopic.lobby.allocation.app.EngineAllocationService;
import io.jhpark.kopic.lobby.engine.app.EnginePrivateRoomPort;
import io.jhpark.kopic.lobby.engine.domain.CreatePrivateRoomCommand;
import io.jhpark.kopic.lobby.room.domain.RoomEntry;
import io.jhpark.kopic.lobby.room.dto.CreatePrivateRoomRequest;
import org.springframework.stereotype.Service;

@Service
public class PrivateRoomCreateService {

    private final EngineAllocationService engineAllocationService;
    private final EnginePrivateRoomPort enginePrivateRoomPort;

    public PrivateRoomCreateService(
            EngineAllocationService engineAllocationService,
            EnginePrivateRoomPort enginePrivateRoomPort
    ) {
        this.engineAllocationService = engineAllocationService;
        this.enginePrivateRoomPort = enginePrivateRoomPort;
    }

    public RoomEntry create(CreatePrivateRoomRequest request) {
        String engineId = engineAllocationService.allocateOwnerEngineId();

        return enginePrivateRoomPort.createPrivateRoom(
                engineId,
                new CreatePrivateRoomCommand(
                        request.userId(),
                        request.name()
                )
        ).roomEntry();
    }
}
