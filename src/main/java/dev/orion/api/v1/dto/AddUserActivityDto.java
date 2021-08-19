package dev.orion.api.v1.dto;

import javax.validation.constraints.NotBlank;
import java.text.MessageFormat;

public class AddUserActivityDto {
    @NotBlank(message = "userId must be not empty/blank")
    public String userId;

    public AddUserActivityDto(){}

    public AddUserActivityDto(String userId){
        this.userId= userId;
    }
}
