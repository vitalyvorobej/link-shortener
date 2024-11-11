package com.course.project.varabei.linkshortener.service.controller;

import com.course.project.varabei.linkshortener.service.dto.ValidationErrorDto;
import com.course.project.varabei.linkshortener.service.dto.response.CommonResponseDto;
import com.course.project.varabei.linkshortener.service.exception.NotFoundException;
import com.course.project.varabei.linkshortener.service.exception.NotFoundPageShortLinkException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class ResponseExceptionHandler {

    private final String notFoundPage;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        log.warn(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.TEXT_HTML)
                .body(notFoundPage);
    }

    @ExceptionHandler(NotFoundPageShortLinkException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundPageShortLinkException e) {
        log.warn(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.TEXT_HTML)
                .body(notFoundPage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResponseDto<?> handleInvalidFormatException(HttpMessageNotReadableException e) {
        if (e.getCause() instanceof InvalidFormatException ife) {
            String path = ife.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .collect(Collectors.joining("."));

            String errMessage = "Ошибка валидации, указан некорректный формат поля '" + path + "'";

            log.error(errMessage, e);

            return CommonResponseDto.builder()
                    .errorMessage(errMessage)
                    .build();
        }

        return handleAllException(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponseDto<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        List<ValidationErrorDto> errorDtoList = bindingResult.getFieldErrors().stream()
                .map(er -> ValidationErrorDto.builder()
                        .field(er.getField())
                        .message(er.getDefaultMessage())
                        .build())
                .toList();

        log.warn("Validation exception: {}", errorDtoList, e);

        return CommonResponseDto.builder()
                .id(UUID.randomUUID())
                .errorMessage("Validation exception ")
                .validationErrorList(errorDtoList)
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public CommonResponseDto<?> handleAllException(Exception e) {
        log.warn("Something went wrong: {}", e.getMessage(), e);

        return CommonResponseDto.builder()
                .id(UUID.randomUUID())
                .errorMessage("Something went wrong " + e.getMessage())
                .build();
    }
}
