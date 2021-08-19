package dev.orion.api.v1.sockets;


import dev.orion.api.v1.sockets.room.ActivityRoom;
import dev.orion.broker.producer.EditorQueueProducer;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;


import java.util.Map;
import java.util.Optional;
import java.util.UUID;



@ServerEndpoint("/v1/activity/{uuidActivity}/{uuidUser}")
@ApplicationScoped
public class ActivitySocket {
    @Inject
    ActivityRoom activityRoom;


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
    public void onMessage(String message,@PathParam("uuidActivity")String activityUuid) {
        activityRoom.broadcast(message,UUID.fromString(activityUuid));
    }


}
