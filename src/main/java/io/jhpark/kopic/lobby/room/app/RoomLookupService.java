package io.jhpark.kopic.lobby.room.app;

import io.jhpark.kopic.lobby.directory.app.RoomRoutingReader;
import io.jhpark.kopic.lobby.room.domain.RoomEntry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RoomLookupService {

    private final RoomCodeLookupReader roomCodeLookupReader;
    private final RoomEntryReader roomEntryReader;
    private final RoomRoutingReader roomRoutingReader;

    public RoomLookupService(
            RoomCodeLookupReader roomCodeLookupReader,
            RoomEntryReader roomEntryReader,
            RoomRoutingReader roomRoutingReader
    ) {
        this.roomCodeLookupReader = roomCodeLookupReader;
        this.roomEntryReader = roomEntryReader;
        this.roomRoutingReader = roomRoutingReader;
    }

    public RoomEntry findByCode(String roomCode) {
        String roomId = roomCodeLookupReader.findRoomIdByCode(roomCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "room code not found"));

        roomRoutingReader.findByRoomId(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "room not found"));

        return roomEntryReader.findByRoomId(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "room not found"));
    }
}
