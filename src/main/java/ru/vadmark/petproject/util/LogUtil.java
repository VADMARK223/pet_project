package ru.vadmark.petproject.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Markitanov Vadim
 * @since 25.01.2021
 */
@Slf4j
public class LogUtil {

    public static void logHeaders(HttpServletResponse response) {
        log.info("==============HEADER==============");
        response.getHeaderNames().forEach(s -> log.info("Header(" + s + "): {}.", response.getHeader(s)));
    }
    public static void setHeaders(HttpServletRequest request, HttpServletResponse response) {
        final String origin = request.getHeader(HttpHeaders.ORIGIN);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "OPTIONS, POST, GET, DELETE");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept, Authorization");
    }
}
