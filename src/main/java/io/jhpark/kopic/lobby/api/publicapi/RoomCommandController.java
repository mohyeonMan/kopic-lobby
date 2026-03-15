package io.jhpark.kopic.lobby.api.publicapi;

import io.jhpark.kopic.lobby.room.app.PrivateRoomCreateService;
import io.jhpark.kopic.lobby.room.dto.CreatePrivateRoomRequest;
import io.jhpark.kopic.lobby.room.dto.CreatePrivateRoomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lobby/rooms")
public class RoomCommandController {

    private final PrivateRoomCreateService privateRoomCreateService;

    public RoomCommandController(PrivateRoomCreateService privateRoomCreateService) {
        this.privateRoomCreateService = privateRoomCreateService;
    }

    @PostMapping("/private")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatePrivateRoomResponse createPrivateRoom(@RequestBody CreatePrivateRoomRequest request) {
        return CreatePrivateRoomResponse.from(privateRoomCreateService.create(request));
    }
}
