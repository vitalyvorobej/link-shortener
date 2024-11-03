package com.course.project.varabei.linkshortener.service.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import static com.course.project.varabei.linkshortener.service.utils.HttpRequestUtils.getFormattedQuery;

@Slf4j
@Component
public class LoggingFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String method = request.getMethod();
        String uri = request.getRequestURI() + getFormattedQuery(request);
        String headers = inlineHeaders(request);

        log.info("Request: {} {} {}", method, uri, headers);

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            super.doFilter(request, responseWrapper, chain);

            String responseBody = "body=" + new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
            log.info("Response: {} {} {} {}", method, uri, response.getStatus(), responseBody);

        } finally {
            responseWrapper.copyBodyToResponse();
        }
    }

    private static String inlineHeaders(HttpServletRequest request) {
        Map<String, String> headersMap = Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors
                        .toMap(it -> it, request::getHeader));

        String inlineHeaders = headersMap.entrySet()
                .stream()
                .map(entry -> {
                    String headerName = entry.getKey();
                    String headerValue = entry.getValue();
                    return headerName + "=" + headerValue;
                })
                .collect(Collectors.joining(","));

        return "headers={" + inlineHeaders + "}";
    }
}