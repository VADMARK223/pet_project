package ru.vadmark.petproject.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import ru.vadmark.petproject.entity.UserEntity;

/**
 * Author: Markitanov Vadim
 * Date: 09.01.2021
 */
@Slf4j
public class WebsocketLogoutEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public WebsocketLogoutEvent(UserEntity source) {
        super(source);
//        log.info(": {}.", source);
    }

    @Override
    public UserEntity getSource() {
        return (UserEntity) super.getSource();
    }
}
