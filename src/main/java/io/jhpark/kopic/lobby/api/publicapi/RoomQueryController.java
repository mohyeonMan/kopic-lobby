package io.jhpark.kopic.lobby.api.publicapi;

import io.jhpark.kopic.lobby.room.app.RoomLookupService;
import io.jhpark.kopic.lobby.room.dto.RoomLookupResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lobby/rooms")
public class RoomQueryController {

    private final RoomLookupService roomLookupService;

    public RoomQueryController(RoomLookupService roomLookupService) {
        this.roomLookupService = roomLookupService;
    }

    @GetMapping("/by-code/{roomCode}")
    public RoomLookupResponse findByCode(@PathVariable String roomCode) {
        return RoomLookupResponse.from(roomLookupService.findByCode(roomCode));
    }
}
