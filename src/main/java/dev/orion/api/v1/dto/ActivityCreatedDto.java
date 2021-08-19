package dev.orion.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityCreatedDto {
    public String uuid;

    public ActivityCreatedDto(){}

    public ActivityCreatedDto(String uuid){
        this.uuid = uuid;
    }
}
