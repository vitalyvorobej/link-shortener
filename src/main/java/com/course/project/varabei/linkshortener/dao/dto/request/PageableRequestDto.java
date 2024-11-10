package com.course.project.varabei.linkshortener.dao.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageableRequestDto {
    @NotNull(message = "The page number cannot be null")
    @Positive(message = "Page number cannot be less than 1")
    private Integer number;

    @NotNull(message = "Page size not set")
    @Positive(message = "Page size cannot be less than 1")
    private Integer size;

    @Valid
    @Builder.Default
    private List<SortRequestDto> sorts = new ArrayList<>();
}