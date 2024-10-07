package com.course.project.varabei.linkshortener.service.impl;

import com.course.project.varabei.linkshortener.dao.request.dto.CreateLinkInfoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class LinkInfoServiceImplTest {

    private CreateLinkInfoRequest createLinkInfoRequest;

    @BeforeEach
    void setUp() {
        createLinkInfoRequest = new CreateLinkInfoRequest();
        createLinkInfoRequest.setLink("https://testLink.com");
        createLinkInfoRequest.setEndTime(LocalDateTime.now().plusHours(1L));
        createLinkInfoRequest.setDescription("Some test link description");
        createLinkInfoRequest.setActive(true);
    }

    @Test
    void getShortLink() {
        LinkInfoServiceImpl linkInfoService = new LinkInfoServiceImpl();
        String shortLink = linkInfoService.getShortLink(createLinkInfoRequest);

        assertNotNull(shortLink);
        assertNotNull(createLinkInfoRequest.getLink());
        assertNotNull(createLinkInfoRequest.getActive());
        assertNotNull(createLinkInfoRequest.getDescription());
        assertNotNull(createLinkInfoRequest.getEndTime());
    }

}