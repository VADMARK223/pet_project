package ru.vadmark.petproject.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.vadmark.petproject.repository.UserEntityRepository;

/**
 * Author: Markitanov Vadim
 * Date: 09.01.2021
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler implements ApplicationListener<WebsocketLogoutEvent> {
    private final UserEntityRepository userRepo;

    @Override
    public void onApplicationEvent(@NonNull WebsocketLogoutEvent event) {
//        log.info("Event source: {}.", event.getSource());
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        log.info("After connection established session: {}.", session);
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws Exception {
        log.info("Handle text message: {}.", message);
        WebSocketMessage webSocketMessage = new ObjectMapper().readValue(message.getPayload(), WebSocketMessage.class);
        log.info("User: {}.", webSocketMessage.getUsername());
        log.info("Password: {}.", webSocketMessage.getPassword());

        //UserEntity user = userRepo.findByUsername("a");

        super.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        log.info("After connection closed: {}.", status);
    }
}
