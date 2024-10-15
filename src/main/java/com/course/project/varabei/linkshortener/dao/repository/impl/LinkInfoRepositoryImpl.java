package com.course.project.varabei.linkshortener.dao.repository.impl;

import com.course.project.varabei.linkshortener.dao.model.LinkInfo;
import com.course.project.varabei.linkshortener.dao.repository.LinkInfoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class LinkInfoRepositoryImpl implements LinkInfoRepository {

    private final ConcurrentMap<String, LinkInfo> linkInfoMap = new ConcurrentHashMap<>();

    @Override
    public Optional<LinkInfo> findByShortLink(String shortLink) {
        return Optional.ofNullable(linkInfoMap.get(shortLink));
    }

    @Override
    public LinkInfo save(LinkInfo linkInfoModel) {
        linkInfoModel.setId(UUID.randomUUID());
        linkInfoMap.put(linkInfoModel.getShortLink(), linkInfoModel);
        return linkInfoModel;
    }

    @Override
    public List<LinkInfo> findAll() {
        return linkInfoMap.values().stream().toList();
    }
}
