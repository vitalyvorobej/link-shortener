package com.course.project.varabei.linkshortener.dao.repository.impl;

import com.course.project.varabei.linkshortener.dao.dto.request.UpdateLinkInfoRequestDto;
import com.course.project.varabei.linkshortener.dao.dto.response.LinkInfoResponseDto;
import com.course.project.varabei.linkshortener.dao.mapper.response.UpdateLinkInfoDaoResponseMapper;
import com.course.project.varabei.linkshortener.dao.model.LinkInfo;
import com.course.project.varabei.linkshortener.dao.repository.LinkInfoRepository;
import com.course.project.varabei.linkshortener.service.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
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

    @Override
    public void deleteById(String id) {
        for (Map.Entry<String, LinkInfo> entry : linkInfoMap.entrySet()) {
            LinkInfo linkInfo = entry.getValue();
            if ((linkInfo.getId().toString()).equals(id)) {
                linkInfoMap.remove(linkInfo.getShortLink());
                log.info("Entry with id {} was removed from the map", id);
                return;
            }
        }
    }

    @Override
    public LinkInfoResponseDto update(UpdateLinkInfoRequestDto updateLinkInfoRequestDto, String newShortLink) {
        String id = updateLinkInfoRequestDto.getId();
        for (Map.Entry<String, LinkInfo> entry : linkInfoMap.entrySet()) {
            LinkInfo linkInfo = entry.getValue();
            if ((linkInfo.getId().toString()).equals(id)) {
                UpdateLinkInfoDaoResponseMapper.INSTANCE.updateLinkInfoFromDto(updateLinkInfoRequestDto, linkInfo);
                linkInfo.setShortLink(newShortLink);
                linkInfoMap.put(linkInfo.getShortLink(), linkInfo);
                log.info("Entry with id {} was updated in the map", id);
                return UpdateLinkInfoDaoResponseMapper.INSTANCE.toResponseDto(linkInfo, updateLinkInfoRequestDto, newShortLink);
            }
        }
        throw new NotFoundException("Object for update was not found");
    }
}
