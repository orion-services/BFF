package dev.orion.broker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


public class ActivityUpdateQueueDto {
    public String uuid;
    public ArrayList<String> participant;
    public String userRound;
    public boolean  isActive;
    public String lastUpdated;
    public String createdBy;
    public ArrayList<String> performErrors;

    public ActivityUpdateQueueDto() {
    }

    public ActivityUpdateQueueDto(String uuid, ArrayList<String> participant, String userRound, boolean isActive, String lastUpdated, String createdBy, ArrayList<String> performErrors) {
        this.uuid = uuid;
        this.participant = participant;
        this.userRound = userRound;
        this.isActive = isActive;
        this.lastUpdated = lastUpdated;
        this.createdBy = createdBy;
        this.performErrors = performErrors;
    }
}
