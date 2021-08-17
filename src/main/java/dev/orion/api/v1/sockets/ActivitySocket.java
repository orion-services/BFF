package dev.orion.api.v1.sockets;


import dev.orion.broker.producer.EditorQueueProducer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("v1/activity/{uuidActivity}")
@ApplicationScoped
public class ActivitySocket {
    @Inject
    EditorQueueProducer editorQueueProducer;

    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("uuidActivity") String activity ){

    }
    @OnClose
    public void onClose(Session session, @PathParam("uuidActivity") String username) {

    }

    @OnError
    public void onError(Session session, @PathParam("uuidActivity") String username, Throwable throwable) {

    }

    @OnMessage
    public void onMessage(String message, @PathParam("uuidActivity") String username) {

    }

    private void broadcast(String message) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result ->  {
                if (result.getException() != null) {
                    System.out.println("Unable to send message: " + result.getException());
                }
            });
        });
    }
}
