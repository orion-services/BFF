package dev.orion.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditorQueueDto {
    public String activityUuid;
    public String externalUserId;
    public String documentContent;
    public LocalDate date;
}
