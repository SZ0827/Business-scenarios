package com.sz.config;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

public class NotificationWebSocketHandler extends TextWebSocketHandler {
    private static final ConcurrentHashMap<Long, WebSocketSession> sessions=new ConcurrentHashMap<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        Long userId=(Long) session.getAttributes().get("userId");
        if(userId!=null){
            sessions.put(userId,session);
        }
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        session.sendMessage(new TextMessage("连接成功！"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        sessions.values().remove(session);
    }

    public void sendNotification(Long userId, String message) throws Exception {
        WebSocketSession session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        }
    }
}
