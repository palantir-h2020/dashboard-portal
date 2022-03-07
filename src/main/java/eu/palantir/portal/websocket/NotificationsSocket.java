package eu.palantir.portal.websocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;

@ServerEndpoint("/websocket/notifications-stream")
@ApplicationScoped
public class NotificationsSocket {
    private static final Logger LOG = Logger.getLogger(NotificationsSocket.class.getName());
    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userID") String userID) {
        sessions.put(userID, session);
        broadcast("User " + userID + " joined");
    }

    @OnClose
    public void onClose(Session session, @PathParam("userID") String userID) {
        sessions.remove(userID);
    }

    @OnError
    public void onError(Session session, @PathParam("userID") String userID, Throwable throwable) {
        sessions.remove(userID);
        LOG.severe(throwable.getMessage());
    }

    @OnMessage
    public void onMessage(String message, @PathParam("userID") String userID) {

    }

    private void broadcast(String message) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result -> {
                if (result.getException() != null) {
                    LOG.severe("Unable to send alert: " + result.getException());
                }
            });
        });
    }
}
