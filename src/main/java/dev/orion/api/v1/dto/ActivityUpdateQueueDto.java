package dev.orion.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityUpdateQueueDto {
    public String uuid;
    public ArrayList<String> participant;
    public String userRound;
    public boolean  isActive;
    public String lastUpdated;
    public String createdBy;
    public ArrayList<String> performErrors;
}
