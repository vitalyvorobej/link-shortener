package com.course.project.varabei.linkshortener.service.impl;

import com.course.project.varabei.linkshortener.configuration.LinkShortenerProperty;
import com.course.project.varabei.linkshortener.dao.dto.request.CreateLinkInfoRequestDto;
import com.course.project.varabei.linkshortener.dao.dto.request.UpdateLinkInfoRequestDto;
import com.course.project.varabei.linkshortener.dao.dto.response.LinkInfoResponseDto;
import com.course.project.varabei.linkshortener.dao.model.LinkInfo;
import com.course.project.varabei.linkshortener.dao.repository.LinkInfoRepository;
import com.course.project.varabei.linkshortener.service.LinkInfoService;
import com.course.project.varabei.linkshortener.service.annotation.ExecutionTimeLog;
import com.course.project.varabei.linkshortener.service.exception.NotFoundException;
import com.course.project.varabei.linkshortener.service.mapper.LinkInfoToResponseDtoMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class LinkInfoServiceImpl implements LinkInfoService {

    private final LinkInfoRepository linkInfoRepository;
    private final LinkShortenerProperty linkShortenerProperty;
    private LinkInfoToResponseDtoMapper linkInfoMapper;

    @Autowired
    public void setLinkInfoMapper(LinkInfoToResponseDtoMapper linkInfoMapper) {
        this.linkInfoMapper = linkInfoMapper;
    }

    @Override
    @ExecutionTimeLog
    public LinkInfoResponseDto createLinkInfo(CreateLinkInfoRequestDto request) {
        String randomString = generateShortLink();

        LinkInfo linkInfo = LinkInfo.builder()
                .shortLink(randomString)
                .openingCount(0L)
                .link(request.getLink())
                .endTime(request.getEndTime())
                .description(request.getDescription())
                .active(request.getActive())
                .build();

        LinkInfo savedLink = linkInfoRepository.save(linkInfo);

        return linkInfoMapper.mapToResponseDto(savedLink);
    }

    @Override
    @ExecutionTimeLog
    public LinkInfoResponseDto getByShortLink(String shortLink) {
        return linkInfoRepository.findByShortLinkAndActiveIsTrueAndEndTimeIsAfter(shortLink)
                .map(linkInfo -> linkInfoMapper.mapToResponseDto(linkInfo)).orElseThrow(() -> new NotFoundException("Link was not found " + shortLink));
    }

    @Override
    @ExecutionTimeLog
    public List<LinkInfoResponseDto> findByFilter() {
        return linkInfoRepository.findAll().stream().map(linkInfo -> linkInfoMapper.mapToResponseDto(linkInfo)).toList();
    }

    @Override
    @ExecutionTimeLog
    public void deleteShortLinkById(UUID shortLink) {
        linkInfoRepository.deleteById(shortLink);
    }

    @Override
    @ExecutionTimeLog
    public LinkInfoResponseDto updateLinkInfo(UpdateLinkInfoRequestDto request) {
        boolean isUpdated = false;

        LinkInfo linkInfo = linkInfoRepository.findById(request);

        if (StringUtils.hasText(request.getLink())) {
            linkInfo.setLink(request.getLink());
            isUpdated = true;
        }

        if (request.getEndTime() != null) {
            linkInfo.setEndTime(request.getEndTime());
            isUpdated = true;
        }

        if (StringUtils.hasText(request.getDescription())) {
            linkInfo.setDescription(request.getDescription());
            isUpdated = true;
        }

        if (request.getActive() != null) {
            linkInfo.setActive(request.getActive());
            isUpdated = true;
        }

        if (isUpdated) {
            linkInfoRepository.save(linkInfo);
        }

        return linkInfoMapper.mapToResponseDto(linkInfo);
    }

    private String generateShortLink() {
        return RandomStringUtils.randomAlphanumeric(linkShortenerProperty.getShortLinkLength());
    }
}
