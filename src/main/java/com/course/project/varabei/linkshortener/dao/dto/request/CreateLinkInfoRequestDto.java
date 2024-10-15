package com.course.project.varabei.linkshortener.dao.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateLinkInfoRequestDto {
    private String link;
    private LocalDateTime endTime;
    private String description;
    private Boolean active;
}
