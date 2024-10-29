package com.course.project.varabei.linkshortener.dao.repository;

import com.course.project.varabei.linkshortener.dao.dto.request.UpdateLinkInfoRequestDto;
import com.course.project.varabei.linkshortener.dao.model.LinkInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface LinkInfoRepository {

    Optional<LinkInfo> findByShortLinkAndActiveIsTrueAndEndTimeIsAfter(String shortLink);

    LinkInfo save(LinkInfo linkInfoModel);

    List<LinkInfo> findAll();

    void deleteById(UUID id);

    LinkInfo findById(UpdateLinkInfoRequestDto updateLinkInfoRequestDto);
}
