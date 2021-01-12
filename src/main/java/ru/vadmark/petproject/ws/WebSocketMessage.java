package ru.vadmark.petproject.ws;

import lombok.Data;

/**
 * Author: Markitanov Vadim
 * Date: 12.01.2021
 */
@Data
public class WebSocketMessage {
    private String username;
    private String password;
}
