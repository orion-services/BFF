package dev.orion.api.v1.sockets.room;


import io.quarkus.arc.log.LoggerName;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class ActivityRoom {
    @LoggerName("ActivityRoom")
    Logger logger;
    Map <UUID, Map<UUID, Session>> rooms = null;

    public ActivityRoom(){
        this.rooms = new ConcurrentHashMap<>();
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
    }


    public void broadcast(String message, UUID activityUuid) {
        Map<UUID, Session> sessions = rooms.get(activityUuid);
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result ->  {
                if (result.getException() != null) {
                    logger.error("Unable to send message: " + result.getException());
                }
            });
        });
    }

}
