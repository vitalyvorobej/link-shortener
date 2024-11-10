package com.course.project.varabei.linkshortener.dao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class LinkInfo extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String shortLink;
    private Long openingCount;
    private String link;
    private LocalDateTime endTime;
    private String description;
    private Boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkInfo linkInfo = (LinkInfo) o;
        return Objects.equals(id, linkInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
