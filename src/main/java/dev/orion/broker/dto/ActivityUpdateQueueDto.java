package dev.orion.broker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import dev.orion.api.v1.model.Activity;
import dev.orion.broker.model.ErrorType;
import dev.orion.broker.model.UserError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

public class ActivityUpdateQueueDto {
    @JsonDeserialize(using = UUIDDeserializer.class)
    public UUID uuid;
    public List<String> participants;
    public String participantRound;
    public Boolean isActive;
    public Set<UserError> performErrors;
    public UserError userError;
    public ErrorType errorType;

//    @SuppressWarnings("unchecked")
//    @JsonProperty("performErrors")
//    private void deserializePerformErrors(List<Map<String, Object>> map) {
//
//        var jsonPerformErros = map;
//
//        jsonPerformErros.stream().forEach(e -> {
//            var jsonUserError = (Map<String, Object>) e.get("userError");
//            var jsonErrorType = (Map<String, Object>) e.get("type");
//            String externalUserId = (String) jsonUserError.get("externalUserId");
//            Integer code = (Integer) jsonErrorType.get("code");
//            String message = (String) jsonErrorType.get("message");
//            UserError userError = new UserError(externalUserId, new ErrorType(code, message));
//            performErrors.add(userError);
//        });
//    }

    @Override
    public String toString() {
        return "{" +
                "uuid:" + uuid +
                ", participants:" + participants +
                ", participantRound:'" + participantRound +
                ", isActive:" + isActive +
                ", performErrors:" + performErrors +
                ", userError:" + userError +
                ", errorType:" + errorType +
                '}';
    }
}
