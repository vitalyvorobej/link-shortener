package com.course.project.varabei.linkshortener.configuration;

import com.course.project.varabei.linkshortener.dao.repository.LinkInfoRepository;
import com.course.project.varabei.linkshortener.dao.repository.impl.LinkInfoRepositoryImpl;
import com.course.project.varabei.linkshortener.service.LinkInfoService;
import com.course.project.varabei.linkshortener.service.impl.LinkInfoServiceImpl;
import com.course.project.varabei.linkshortener.service.proxy.LogExecutionTimeLinkInfoServiceProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkShortenerConfig {

    @Bean
    LinkInfoRepository linkInfoRepository() {
        return new LinkInfoRepositoryImpl();
    }

    @Bean
    public LinkInfoService linkInfoService(LinkInfoRepository linkInfoRepository) {
        LinkInfoProperty linkInfoProperty = new LinkInfoProperty();
        LinkInfoService linkInfoService = new LinkInfoServiceImpl(linkInfoRepository, linkInfoProperty);
        return new LogExecutionTimeLinkInfoServiceProxy(linkInfoService);
    }
}
