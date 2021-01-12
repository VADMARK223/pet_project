package ru.vadmark.petproject.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import ru.vadmark.petproject.entity.UserEntity;
import ru.vadmark.petproject.ws.WebsocketLogoutEvent;

/**
 * Author: Markitanov Vadim
 * Date: 09.01.2021
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class IdleUserTimeChecker {
    private final ApplicationEventPublisher eventPublisher;

    @Scheduled(fixedRate = 1000L)
    public void dropIdleUsersTask() {
//        log.info("Drop idle user task.");
        UserEntity userEntity = new UserEntity();
        userEntity.setId(999L);
        userEntity.setUsername("Vadmark");
        eventPublisher.publishEvent(new WebsocketLogoutEvent(userEntity));
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(2);
        taskScheduler.setThreadNamePrefix("check-user-task-");
        taskScheduler.setDaemon(true);
        return taskScheduler;
    }
}
