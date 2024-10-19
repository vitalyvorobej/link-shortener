package com.course.project.varabei.linkshortener.dao.repository.impl;

import com.course.project.varabei.linkshortener.dao.dto.request.UpdateLinkInfoRequestDto;
import com.course.project.varabei.linkshortener.dao.dto.response.LinkInfoResponseDto;
import com.course.project.varabei.linkshortener.dao.mapper.response.UpdateLinkInfoDaoResponseMapperImpl;
import com.course.project.varabei.linkshortener.dao.model.LinkInfo;
import com.course.project.varabei.linkshortener.dao.repository.LinkInfoRepository;
import com.course.project.varabei.linkshortener.service.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UpdateLinkInfoDaoResponseMapperImpl updateLinkInfoDaoResponseMapper;

    @Autowired
    public void setUpdateLinkInfoDaoResponseMapper(UpdateLinkInfoDaoResponseMapperImpl updateLinkInfoDaoResponseMapper) {
        this.updateLinkInfoDaoResponseMapper = updateLinkInfoDaoResponseMapper;
    }

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
    public void deleteById(UUID id) {
        String shortLinkForDelete = searchShortLinkInMapById(id);
        linkInfoMap.remove(shortLinkForDelete);
        log.info("Deleted short_link from the Map : {}", shortLinkForDelete);
    }

    @Override
    public LinkInfoResponseDto update(UpdateLinkInfoRequestDto requestDto) {
        String id = requestDto.getId();
        String dtoShortLinkForUpdate = searchShortLinkInMapById(UUID.fromString(id));

        LinkInfo linkInfo = linkInfoMap.get(dtoShortLinkForUpdate);
        updateLinkInfoDaoResponseMapper.updateLinkInfoFromDto(requestDto, linkInfo);

        log.info("Entry with id {} was updated in the map", id);
        return updateLinkInfoDaoResponseMapper.mapToResponse(requestDto);
    }

    private String searchShortLinkInMapById(UUID id) {
        for (Map.Entry<String, LinkInfo> entry : linkInfoMap.entrySet()) {
            LinkInfo linkInfo = entry.getValue();
            if (linkInfo.getId().equals(id)) {
                return linkInfo.getShortLink();
            }
        }
        throw new NotFoundException("Object was not found in the map");
    }
}
