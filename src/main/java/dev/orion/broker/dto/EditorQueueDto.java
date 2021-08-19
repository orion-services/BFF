package dev.orion.broker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


public class EditorQueueDto {
    public String activityUuid;
    public String externalUserId;
    public String documentContent;
    public LocalDate date;

    public EditorQueueDto(){
        this.date = LocalDate.now();
    }

    public EditorQueueDto(String activityUuid, String externalUserId, String documentContent) {
        this.activityUuid = activityUuid;
        this.externalUserId = externalUserId;
        this.documentContent = documentContent;
        this.date = LocalDate.now();
    }
}
