package com.course.project.varabei.linkshortener.service;

import com.course.project.varabei.linkshortener.dao.dto.request.CreateLinkInfoRequestDto;
import com.course.project.varabei.linkshortener.dao.dto.response.LinkInfoResponseDto;

import java.util.List;

public interface LinkInfoService {

    LinkInfoResponseDto createLinkInfo(CreateLinkInfoRequestDto request);

    LinkInfoResponseDto getByShortLink(String shortLink);

    List<LinkInfoResponseDto> findByFilter();

}
