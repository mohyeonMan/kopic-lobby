package io.jhpark.kopic.lobby.engine.infra;

import io.jhpark.kopic.lobby.engine.domain.EnginePresence;
import io.jhpark.kopic.lobby.engine.domain.EngineStatus;
import io.jhpark.kopic.lobby.room.domain.RoomEntry;
import io.jhpark.kopic.lobby.room.domain.RoomType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

/**
 * Dummy sample in-memory state store.
 * Replace this class with real Redis/GE-backed implementations later.
 */
@Component
public class DummySampleLobbyStateStore {

    private static final int RANDOM_ROOM_CAPACITY = 8;

    private final Map<String, RoomEntry> roomEntries = new ConcurrentHashMap<>();
    private final Map<String, String> roomCodeToRoomId = new ConcurrentHashMap<>();
    private final Map<String, String> roomOwners = new ConcurrentHashMap<>();
    private final Map<String, Integer> participantCounts = new ConcurrentHashMap<>();
    private final Set<String> joinableRandomRooms = ConcurrentHashMap.newKeySet();
    private final Map<String, EnginePresence> enginePresences = new ConcurrentHashMap<>();

    public DummySampleLobbyStateStore() {
        EnginePresence defaultEngine = new EnginePresence(
                "ge-dummy-1",
                "http://ge-dummy-1:8080",
                EngineStatus.ACTIVE,
                0,
                Instant.now()
        );
        enginePresences.put(defaultEngine.engineId(), defaultEngine);
        EnginePresence secondaryEngine = new EnginePresence(
                "ge-dummy-2",
                "http://ge-dummy-2:8080",
                EngineStatus.ACTIVE,
                0,
                Instant.now()
        );
        enginePresences.put(secondaryEngine.engineId(), secondaryEngine);
    }

    public List<EnginePresence> findActivePresences() {
        return enginePresences.values().stream()
                .filter(presence -> presence.status() == EngineStatus.ACTIVE)
                .toList();
    }

    public synchronized RoomEntry createPrivateRoom(String engineId) {
        RoomEntry roomEntry = new RoomEntry(
                nextRoomId(),
                RoomType.PRIVATE,
                nextRoomCode(),
                engineId
        );
        roomEntries.put(roomEntry.roomId(), roomEntry);
        roomCodeToRoomId.put(roomEntry.roomCode(), roomEntry.roomId());
        roomOwners.put(roomEntry.roomId(), engineId);
        participantCounts.put(roomEntry.roomId(), 1);
        incrementActiveRooms(engineId);
        return roomEntry;
    }

    public synchronized RoomEntry createRandomRoom(String engineId) {
        RoomEntry roomEntry = new RoomEntry(
                nextRoomId(),
                RoomType.RANDOM,
                null,
                engineId
        );
        roomEntries.put(roomEntry.roomId(), roomEntry);
        roomOwners.put(roomEntry.roomId(), engineId);
        participantCounts.put(roomEntry.roomId(), 0);
        joinableRandomRooms.add(roomEntry.roomId());
        incrementActiveRooms(engineId);
        return roomEntry;
    }

    public synchronized Optional<RoomEntry> tryJoinRandomRoom(String engineId, String roomId) {
        RoomEntry roomEntry = roomEntries.get(roomId);
        if (roomEntry == null || roomEntry.roomType() != RoomType.RANDOM) {
            return Optional.empty();
        }
        if (!engineId.equals(roomOwners.get(roomId))) {
            return Optional.empty();
        }

        int participants = participantCounts.getOrDefault(roomId, 0);
        if (participants >= RANDOM_ROOM_CAPACITY) {
            joinableRandomRooms.remove(roomId);
            return Optional.empty();
        }

        int updated = participants + 1;
        participantCounts.put(roomId, updated);
        if (updated >= RANDOM_ROOM_CAPACITY) {
            joinableRandomRooms.remove(roomId);
        }
        return Optional.of(roomEntry);
    }

    public List<String> findJoinableRandomRoomIds(int limit) {
        return new ArrayList<>(joinableRandomRooms).stream()
                .limit(limit)
                .toList();
    }

    public Optional<String> findRoomIdByCode(String roomCode) {
        return Optional.ofNullable(roomCodeToRoomId.get(roomCode));
    }

    public Optional<RoomEntry> findRoomEntry(String roomId) {
        return Optional.ofNullable(roomEntries.get(roomId));
    }

    public Optional<String> findOwnerEngineId(String roomId) {
        return Optional.ofNullable(roomOwners.get(roomId));
    }

    public synchronized void updateOwner(String roomId, String ownerEngineId) {
        RoomEntry existing = roomEntries.get(roomId);
        if (existing == null) {
            return;
        }
        roomOwners.put(roomId, ownerEngineId);
        roomEntries.put(
                roomId,
                new RoomEntry(existing.roomId(), existing.roomType(), existing.roomCode(), ownerEngineId)
        );
    }

    private void incrementActiveRooms(String engineId) {
        EnginePresence presence = enginePresences.get(engineId);
        if (presence == null) {
            return;
        }
        enginePresences.put(
                engineId,
                new EnginePresence(
                        presence.engineId(),
                        presence.endpoint(),
                        presence.status(),
                        presence.activeRooms() + 1,
                        Instant.now()
                )
        );
    }

    private String nextRoomId() {
        return "r-" + UUID.randomUUID().toString().substring(0, 8);
    }

    private String nextRoomCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
    }
}
