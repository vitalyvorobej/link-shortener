package com.course.project.varabei.linkshortener.dao.dto.request;

import com.course.project.varabei.linkshortener.service.validation.ValidUUID;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLinkInfoRequestDto {
    @NotNull
    @ValidUUID
    private String id;
    @NotEmpty(message = "The link can't be empty")
    @Pattern(regexp = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)", message = "Url is not equal to pattern")
    private String link;
    @Future(message = "The date of endTime can't be in the past")
    private LocalDateTime endTime;
    @NotEmpty(message = "The description shouldn't be empty")
    private String description;
    @NotNull(message = "You should provide an active status")
    private Boolean active;
}
