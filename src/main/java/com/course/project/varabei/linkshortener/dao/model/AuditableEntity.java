package com.course.project.varabei.linkshortener.dao.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.course.project.varabei.linkshortener.service.utils.Constants.DEFAULT_DB_USER;

@Getter
@Setter
@MappedSuperclass
public class AuditableEntity {

    private LocalDateTime createTime;
    private String createdBy;
    private LocalDateTime updateTime;
    private String updatedBy;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createTime = now;
        this.updateTime = now;
        this.createdBy = DEFAULT_DB_USER;
        this.updatedBy = DEFAULT_DB_USER;
    }

    @PreUpdate
    public void preUpdate() {
        this.updateTime = LocalDateTime.now();
        this.updatedBy = DEFAULT_DB_USER;
    }
}