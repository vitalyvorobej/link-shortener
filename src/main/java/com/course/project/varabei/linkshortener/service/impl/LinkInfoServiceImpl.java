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
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class LinkInfoServiceImpl implements LinkInfoService {

    private final LinkInfoRepository linkInfoRepository;
    private final LinkShortenerProperty linkShortenerProperty;


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

        return linkInfoResponseBuilder(savedLink);
    }

    @Override
    @ExecutionTimeLog
    public LinkInfoResponseDto getByShortLink(String shortLink) {
        return linkInfoRepository.findByShortLink(shortLink)
                .map(LinkInfoServiceImpl::linkInfoResponseBuilder)
                .orElseThrow(() -> new NotFoundException("Link was not found " + shortLink));
    }

    @Override
    @ExecutionTimeLog
    public List<LinkInfoResponseDto> findByFilter() {
        return linkInfoRepository.findAll().stream()
                .map(LinkInfoServiceImpl::linkInfoResponseBuilder).toList();
    }

    @Override
    @ExecutionTimeLog
    public void deleteShortLinkById(UUID shortLink) {
        linkInfoRepository.deleteById(shortLink);
    }

    @Override
    @ExecutionTimeLog
    public LinkInfoResponseDto updateLinkInfo(UpdateLinkInfoRequestDto request) {
        return linkInfoRepository.update(request);
    }


    private String generateShortLink() {
        return RandomStringUtils.randomAlphanumeric(linkShortenerProperty.getShortLinkLength());
    }

    private static LinkInfoResponseDto linkInfoResponseBuilder(LinkInfo link) {
        return LinkInfoResponseDto
                .builder()
                .id(link.getId())
                .shortLink(link.getShortLink())
                .openingCount(link.getOpeningCount())
                .link(link.getLink())
                .endTime(link.getEndTime())
                .description(link.getDescription())
                .active(link.getActive())
                .build();
    }
}
