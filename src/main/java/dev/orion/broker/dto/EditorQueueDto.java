package dev.orion.broker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class EditorQueueDto {
    @NotBlank
    public String uuid;
    @NotBlank
    public String externalUserId;
    @NotBlank
    public String documentContent;


    public EditorQueueDto(){

    }

    public EditorQueueDto(String uuid, String externalUserId, String documentContent) {
        this.uuid = uuid;
        this.externalUserId = externalUserId;
        this.documentContent = documentContent;
    }
}
