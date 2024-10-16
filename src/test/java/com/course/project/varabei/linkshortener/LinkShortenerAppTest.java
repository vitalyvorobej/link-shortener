package com.course.project.varabei.linkshortener;

import com.course.project.varabei.linkshortener.service.LinkInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LinkShortenerAppTest {

    private LinkInfoService linkInfoService;

    @Autowired
    public void setLinkInfoService(LinkInfoService linkInfoService) {
        this.linkInfoService = linkInfoService;
    }

    @Test
    void checkLogging() {
        linkInfoService.findByFilter();
    }
}