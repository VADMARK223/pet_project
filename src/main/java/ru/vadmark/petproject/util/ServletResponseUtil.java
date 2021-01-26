package ru.vadmark.petproject.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Markitanov Vadim
 * @since 25.01.2021
 */
@Slf4j
public class ServletResponseUtil {
    public static void logHeaders(HttpServletResponse response) {
        log.info("==============HEADER==============");
        response.getHeaderNames().forEach(s -> log.info("Header(" + s + "): {}.", response.getHeader(s)));
    }

    public static void setHeaders(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, req.getHeader(HttpHeaders.ORIGIN));
        resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "OPTIONS, POST, GET, DELETE");
        resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        resp.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION);
    }
}
