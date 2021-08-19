package dev.orion.broker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditorUpdateQueueDto {
    @NotBlank
    public String activityUuid;
    @NotBlank
    public String externalUserId;
    @NotBlank
    public String documentContent;
}
