package dev.orion.broker.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserError {
    public String externalUserId;
    public ErrorType type;

    public UserError() {
    }

    public UserError(String externalUserId, ErrorType type) {
        this.externalUserId = externalUserId;
        this.type = type;
    }

//    @JsonProperty("type")
//    public void deserializeType(Map<String, Object> json) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        type = objectMapper.readValue(json.values().toString(), ErrorType.class);
//    }

    @Override
    public String toString() {
        return "{" +
                "externalUserId:'" + externalUserId +
                ", type:" + type +
                '}';
    }
}
