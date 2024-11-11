package com.course.project.varabei.linkshortener.dao.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SortRequestDto {

    @NotEmpty(message = "There is no field for sorting")
    private String field;

    @Builder.Default
    @Pattern(regexp = "ASC|DESC", message = "Incorrect way of sort")
    private String direction = "ASC";
}