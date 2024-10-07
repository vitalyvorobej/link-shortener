package com.course.project.varabei.linkshortener.service;

import com.course.project.varabei.linkshortener.dao.request.dto.CreateLinkInfoRequest;

public interface LinkInfoService {

   String getShortLink(CreateLinkInfoRequest request);

}
