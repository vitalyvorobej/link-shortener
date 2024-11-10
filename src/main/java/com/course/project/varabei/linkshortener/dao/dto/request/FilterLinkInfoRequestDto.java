package com.course.project.varabei.linkshortener.dao.dto.request;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterLinkInfoRequestDto {
    private String linkPart;
    private LocalDateTime endTimeFrom;
    private LocalDateTime endTimeTo;
    private String descriptionPart;
    private Boolean active;

    @Valid
    @Builder.Default
    private PageableRequestDto page = new PageableRequestDto(1, 5, List.of());
}
