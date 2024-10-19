package com.course.project.varabei.linkshortener.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "link-shortener")
public class LinkInfoProperty {
    private Integer shortLinkLength;
}
