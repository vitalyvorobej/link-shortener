package com.course.project.varabei.linkshortener.service.impl;

import com.course.project.varabei.linkshortener.dao.request.dto.CreateLinkInfoRequest;
import com.course.project.varabei.linkshortener.service.LinkInfoService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class LinkInfoServiceImpl implements LinkInfoService {

    private final Map<String, CreateLinkInfoRequest> linkInfoRequestMap = new HashMap<>();

    @Override
    public String getShortLink(CreateLinkInfoRequest request) {
        String randomString = RandomStringUtils.randomAlphanumeric(7);
        linkInfoRequestMap.put(randomString, request);
        return randomString;
    }
}
