package dev.orion.api.v1.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Activity {

    public UUID uuid;

    public String document;

    public List<UUID> userList = new ArrayList<>();

    public UUID userRound;

    public UUID createdBy;

    public Boolean isActive;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    public Activity() {
    }

    public Activity(UUID uuid, String document, List<UUID> userList, UUID userRound, UUID createdBy, Boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.uuid = uuid;
        this.document = document;
        this.userList = userList;
        this.userRound = userRound;
        this.createdBy = createdBy;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
