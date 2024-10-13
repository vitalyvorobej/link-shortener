package com.course.project.varabei.linkshortener.dao.repository;

import com.course.project.varabei.linkshortener.dao.model.LinkInfo;

import java.util.List;
import java.util.Optional;


public interface LinkInfoRepository {

    Optional<LinkInfo> findByShortLink(String shortLink);

    LinkInfo save(LinkInfo linkInfoModel);

    List<LinkInfo> findAll();
}
