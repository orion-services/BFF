package dev.orion.api.v1.sockets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.orion.api.v1.resource.ActivityResourceV1;
import dev.orion.api.v1.sockets.room.ActivityRoom;
import dev.orion.broker.dto.EditorQueueDto;
import dev.orion.broker.dto.EditorUpdateQueueDto;
import dev.orion.broker.producer.EditorQueueProducer;
import io.quarkus.arc.log.LoggerName;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import javax.validation.ValidatorFactory;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.UUID;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import dev.orion.api.v1.client.ActivityClient;
import dev.orion.api.v1.dto.AddUserActivityDto;

@ServerEndpoint("/v1/activity/{uuidActivity}/{uuidUser}")
@ApplicationScoped
public class ActivitySocket {
    @Inject
    ActivityRoom activityRoom;

    @Inject
    ValidatorFactory validatorFactory;

    @Inject
    ActivityResourceV1 activityResourceV1;

    @Inject
    EditorQueueProducer editorQueueProducer;

    @Inject
    @RestClient
    ActivityClient activityClient;

    @LoggerName("ActivitySocket")
    Logger logger;

    @OnOpen
    public void onOpen(Session session, @PathParam("uuidUser") String userUuid,
            @PathParam("uuidActivity") String activityUuid) {

        AddUserActivityDto addUserActivityDto = new AddUserActivityDto(userUuid);
        try {
            activityClient.addUserToActivity(activityUuid, addUserActivityDto);
            activityRoom.addUserToRoom(UUID.fromString(activityUuid), UUID.fromString(userUuid), session);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("uuidUser") String userUuid,
            @PathParam("uuidActivity") String activityUuid) {
        activityClient.disconnectUserFromActivity(activityUuid, userUuid);
        activityRoom.removeUserFromRoom(UUID.fromString(activityUuid), UUID.fromString(userUuid), session);
    }

    @OnError
    public void onError(Session session, @PathParam("uuidUser") String userUuid,
            @PathParam("uuidActivity") String activityUuid, Throwable throwable) {
        logger.warn(throwable);
        // activityRoom.removeUserFromRoom(UUID.fromString(activityUuid),UUID.fromString(userUuid),session);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("uuidActivity") String activityUuid,
            @PathParam("uuidUser") String userUuid) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        EditorQueueDto editorQueueDto = objectMapper.readValue(message, EditorQueueDto.class);
        editorQueueDto.setExternalUserId(userUuid);
        editorQueueDto.setUuid(activityUuid);
        editorQueueProducer.sendMessage(editorQueueDto);

    }

}
