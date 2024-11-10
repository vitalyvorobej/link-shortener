package com.course.project.varabei.linkshortener.service.impl;

import com.course.project.varabei.linkshortener.configuration.LinkShortenerProperty;
import com.course.project.varabei.linkshortener.dao.dto.request.CreateLinkInfoRequestDto;
import com.course.project.varabei.linkshortener.dao.dto.request.FilterLinkInfoRequestDto;
import com.course.project.varabei.linkshortener.dao.dto.request.PageableRequestDto;
import com.course.project.varabei.linkshortener.dao.dto.request.UpdateLinkInfoRequestDto;
import com.course.project.varabei.linkshortener.dao.dto.response.LinkInfoResponseDto;
import com.course.project.varabei.linkshortener.dao.model.LinkInfo;
import com.course.project.varabei.linkshortener.dao.repository.LinkInfoRepository;
import com.course.project.varabei.linkshortener.service.LinkInfoService;
import com.course.project.varabei.linkshortener.service.annotation.ExecutionTimeLog;
import com.course.project.varabei.linkshortener.service.exception.NotFoundException;
import com.course.project.varabei.linkshortener.service.exception.NotFoundPageShortLinkException;
import com.course.project.varabei.linkshortener.service.mapper.request.LinkInfoFromRequestDtoMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class LinkInfoServiceImpl implements LinkInfoService {

    private final LinkInfoRepository linkInfoRepository;
    private final LinkShortenerProperty linkShortenerProperty;
    private final LinkInfoFromRequestDtoMapper linkInfoFromRequestDtoMapper;

    @Override
    @ExecutionTimeLog
    public LinkInfoResponseDto createLinkInfo(CreateLinkInfoRequestDto request) {
        String randomString = generateShortLink();

        LinkInfo linkInfo = linkInfoFromRequestDtoMapper.fromRequestDto(request, randomString);

        LinkInfo savedLink = linkInfoRepository.save(linkInfo);

        return linkInfoFromRequestDtoMapper.mapToResponseDto(savedLink);
    }

    @Override
    @ExecutionTimeLog
    public LinkInfoResponseDto getByShortLink(String shortLink) {
        LinkInfo activeShortLink = linkInfoRepository.findActiveShortLink(shortLink, LocalDateTime.now())
                .orElseThrow(() -> new NotFoundPageShortLinkException("Page link was not found " + shortLink));

        linkInfoRepository.incrementOpeningCount(shortLink);

        return linkInfoFromRequestDtoMapper.mapToResponseDto(activeShortLink);
    }

    @Override
    @ExecutionTimeLog
    public List<LinkInfoResponseDto> findByFilter(FilterLinkInfoRequestDto filterRequest) {
        PageableRequestDto page = filterRequest.getPage();

        Pageable pageable = mapPageable(page);

        return linkInfoRepository.findByFilter(
                        filterRequest.getLinkPart(),
                        filterRequest.getEndTimeFrom(),
                        filterRequest.getEndTimeTo(),
                        filterRequest.getDescriptionPart(),
                        filterRequest.getActive(),
                        pageable
                )
                .stream()
                .map(linkInfoFromRequestDtoMapper::mapToResponseDto)
                .toList();
    }

    @Override
    @ExecutionTimeLog
    public void deleteShortLinkById(UUID shortLink) {
        linkInfoRepository.deleteById(shortLink);
    }

    @Override
    @ExecutionTimeLog
    public LinkInfoResponseDto updateLinkInfo(UpdateLinkInfoRequestDto request) {

        if (!StringUtils.hasText(request.getLink()) &&
                request.getEndTime() == null &&
                !StringUtils.hasText(request.getDescription()) &&
                request.getActive() == null) {
            throw new IllegalArgumentException("No fields provided for update");
        }

        boolean isUpdated = false;
        UUID id = UUID.fromString(request.getId());

        LinkInfo linkInfo = linkInfoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Link was not found " + id));

        if (StringUtils.hasText(request.getLink())) {
            linkInfo.setLink(request.getLink());
            isUpdated = true;
        }

        linkInfo.setEndTime(request.getEndTime());

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

        return linkInfoFromRequestDtoMapper.mapToResponseDto(linkInfo);
    }

    private String generateShortLink() {
        return RandomStringUtils.randomAlphanumeric(linkShortenerProperty.getShortLinkLength());
    }

    private Pageable mapPageable(PageableRequestDto page) {
        List<Sort.Order> sorts = page.getSorts().stream()
                .map(sort -> new Sort.Order(
                        Sort.Direction.valueOf(sort.getDirection()),
                        sort.getField()
                ))
                .toList();

        return PageRequest.of(page.getNumber() - 1, page.getSize(), Sort.by(sorts));
    }
}
