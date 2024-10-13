package com.course.project.varabei.linkshortener.dao.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkInfoResponseDto {
    private UUID id;
    private String shortLink;
    private Long openingCount;
    private String link;
    private LocalDateTime endTime;
    private String description;
    private Boolean active;
}
