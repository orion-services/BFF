package dev.orion.api.v1.sockets;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.orion.api.v1.sockets.room.ActivityRoom;
import dev.orion.broker.dto.EditorUpdateQueueDto;
import dev.orion.broker.producer.EditorQueueProducer;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidatorFactory;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;


import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;



@ServerEndpoint("/v1/activity/{uuidActivity}/{uuidUser}")
@ApplicationScoped
public class ActivitySocket {
    @Inject
    ActivityRoom activityRoom;

    @Inject
    ValidatorFactory validatorFactory;

    @Inject
    EditorQueueProducer editorQueueProducer;

    @OnOpen
    public void onOpen(Session session, @PathParam("uuidUser") String userUuid, @PathParam("uuidActivity") String activityUuid ){
        activityRoom.addUserToRoom(UUID.fromString(activityUuid),UUID.fromString(userUuid),session);
    }
    @OnClose
    public void onClose(Session session, @PathParam("uuidUser") String userUuid,@PathParam("uuidActivity") String activityUuid) {
        activityRoom.removeUserFromRoom(UUID.fromString(activityUuid),UUID.fromString(userUuid),session);
    }

    @OnError
    public void onError(Session session, @PathParam("uuidUser") String userUuid,@PathParam("uuidActivity") String activityUuid, Throwable throwable) {
        activityRoom.removeUserFromRoom(UUID.fromString(activityUuid),UUID.fromString(userUuid),session);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("uuidActivity")String activityUuid) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        EditorUpdateQueueDto editorUpdateQueueDto = objectMapper.readValue(message,EditorUpdateQueueDto.class);
        //Validation isn't working yet
//        var validator = validatorFactory.getValidator();
//        Set<ConstraintViolation<EditorUpdateQueueDto>> errorList =  validator.validate(editorUpdateQueueDto, EditorUpdateQueueDto.class);
//        if (!errorList.isEmpty()) {
//
//            return;
//        }
        editorQueueProducer.sendMessage(editorUpdateQueueDto);
        activityRoom.broadcast(message,UUID.fromString(activityUuid));
    }


}
