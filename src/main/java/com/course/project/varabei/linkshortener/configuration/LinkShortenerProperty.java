package com.course.project.varabei.linkshortener.configuration;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Setter
@Getter
@Validated
@Component
@ConfigurationProperties("link-shortener")
public class LinkShortenerProperty {
    @Min(value = 6, message = "Minimum 6 digits for the link length generator")
    @NotNull
    private Integer shortLinkLength;
}
