package com.course.project.varabei.linkshortener.service.exception;

public class LinkShortenerException extends RuntimeException {

    public LinkShortenerException(String message) {
        super(message);
    }

    public LinkShortenerException(String message, Exception exception) {super(message, exception);}
}
