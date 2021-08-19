package dev.orion.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditorUpdateQueueDto {
    public String activityUuid;
    public String externalUserId;
    public String documentContent;
}
