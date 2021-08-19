package dev.orion.broker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditorUpdateQueueDto {

    public String activityUuid;

    public String externalUserId;

    public String documentContent;
}
