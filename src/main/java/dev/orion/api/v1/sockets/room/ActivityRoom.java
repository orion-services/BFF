package dev.orion.api.v1.sockets.room;


import dev.orion.broker.dto.ActivityUpdateQueueDto;
import io.quarkus.arc.log.LoggerName;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import javax.websocket.Session;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class ActivityRoom {
    @LoggerName("ActivityRoom")
    Logger logger;

    static Map <UUID, Map<UUID, Session>> rooms;
    //Map<UUID, ActivityBroadcastDto> messageBody;

    public ActivityRoom(){
        this.rooms = new HashMap<>();
    }
    public void updateMessageActivity(UUID activityUuid, ActivityUpdateQueueDto message){

    }

    public Optional<Session> getSessionByUserId(String userId) {
        return rooms.values()
                .stream()
                .filter(map -> map.containsKey(userId))
                .map(sessionMap -> sessionMap.get(userId))
                .findFirst();
    }

    public void addUserToRoom(UUID activityUuid, UUID userUuid, Session session) {
        Map<UUID, Session> sessionMap = Optional
                .ofNullable(rooms.get(activityUuid)).orElse(new ConcurrentHashMap<>());

        sessionMap.put(userUuid, session);

        rooms.put(activityUuid, sessionMap);

        logger.info(MessageFormat.format("User {0} inserted in activity {1}", userUuid, activityUuid));
    }

    public void removeUserFromRoom(UUID activityUuid, UUID userUuid, Session session){
        Map<UUID, Session> sessionMap = Optional
                .ofNullable(rooms.get(activityUuid)).orElse(new ConcurrentHashMap<>());

        sessionMap.remove(userUuid, session);
        logger.info(MessageFormat.format("User {0} removed from activity {1}", userUuid, activityUuid));
    }


    public void broadcast(String message, UUID activityUuid) {
        if(rooms.containsKey(activityUuid)) {
            Map<UUID, Session> sessions = rooms.get(activityUuid);
            sessions.values().forEach(s -> {
                s.getAsyncRemote().sendObject(message, result -> {
                    if (result.getException() != null) {
                        logger.error("Unable to send message: " + result.getException());
                    }
                });
            });
        }else {
            logger.warn(MessageFormat.format("Room {0} not found",activityUuid));
        }
    }

}
