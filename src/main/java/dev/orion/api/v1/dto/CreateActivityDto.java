package dev.orion.api.v1.dto;

import javax.validation.constraints.NotBlank;

public class CreateActivityDto {
    @NotBlank(message = "userExternalId may not be blank")
    public String userExternalId;
}
