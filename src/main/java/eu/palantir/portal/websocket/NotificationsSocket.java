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

import eu.palantir.portal.dto.message.FrontendNotification;
import eu.palantir.portal.websocket.encoder.FrontendNotificationEncoder;

import javax.websocket.Session;

@ServerEndpoint(value = "/websocket/notifications-stream/{userID}", encoders = {
        FrontendNotificationEncoder.class }, decoders = {})
@ApplicationScoped
public class NotificationsSocket {
    // CHANGE LATER: Handle more than one sessions for a single user.
    // CHANGE LATER: Add authentication and filtering based on userID.

    private static final Logger LOG = Logger.getLogger(NotificationsSocket.class.getName());
    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userID") String userID) {
        sessions.put(userID, session);

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

    public void broadcast(String message) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result -> {
                if (result.getException() != null) {
                    LOG.severe("Unable to send notification: " + result.getException());
                }
            });
        });
    }

    public void sendNotification(FrontendNotification notification) {
        // CHANGE LATER: Send notification to specific users.
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(notification, result -> {
                if (result.getException() != null) {
                    LOG.severe("Unable to send notification: " + result.getException());
                }
            });
        });
    }
}
