package com.course.project.varabei.linkshortener.service.dto.response;

import com.course.project.varabei.linkshortener.service.dto.ValidationErrorDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponseDto<T> {
    private UUID id;
    private T body;
    private String errorMessage;
    private List<ValidationErrorDto> validationErrorList;
}
