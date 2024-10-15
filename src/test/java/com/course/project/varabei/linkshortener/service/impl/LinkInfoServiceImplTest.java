package com.course.project.varabei.linkshortener.service.impl;

import com.course.project.varabei.linkshortener.dao.dto.request.CreateLinkInfoRequestDto;
import com.course.project.varabei.linkshortener.dao.dto.response.LinkInfoResponseDto;
import com.course.project.varabei.linkshortener.dao.model.LinkInfo;
import com.course.project.varabei.linkshortener.dao.repository.impl.LinkInfoRepositoryImpl;
import com.course.project.varabei.linkshortener.service.exception.NotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;


class LinkInfoServiceImplTest {

    @InjectMocks
    private LinkInfoServiceImpl linkInfoService;
    @Mock
    private LinkInfoRepositoryImpl linkInfoRepository;

    private static final LocalDateTime now = LocalDateTime.now();
    private final String testLink = "https://test-link.com";
    private final String description = "description for test";
    private final String randomString = RandomStringUtils.randomAlphanumeric(10);


    @BeforeEach
    void setUp() {
        openMocks(this);
        linkInfoService = new LinkInfoServiceImpl(linkInfoRepository);
    }

    @Test
    void createLinkInfo() {
        CreateLinkInfoRequestDto createLinkInfoRequestDto = getCreateLinkInfoRequest();

        LinkInfo linkInfo = getData();

        when(linkInfoRepository.save(any(LinkInfo.class))).thenReturn(linkInfo);

        LinkInfoResponseDto response = linkInfoService.createLinkInfo(createLinkInfoRequestDto);

        assertEquals(10, response.getShortLink().length());
        assertEquals(getData().getShortLink(), response.getShortLink());
        assertEquals(testLink, response.getLink());
        assertEquals(description, response.getDescription());
        assertEquals(Boolean.TRUE, response.getActive());
        assertEquals(now, response.getEndTime());

        verify(linkInfoRepository, times(1)).save(any(LinkInfo.class));
    }

    @Test
    void getByShortLink() {
        LinkInfo linkInfo = LinkInfo
                .builder()
                .shortLink(randomString)
                .link(testLink)
                .build();

        when(linkInfoRepository.findByShortLink(randomString)).thenReturn(Optional.of(linkInfo));

        LinkInfoResponseDto response = linkInfoService.getByShortLink(randomString);

        assertNotNull(response);
        assertEquals(randomString, response.getShortLink());
        assertEquals(testLink, response.getLink());

        verify(linkInfoRepository, times(1)).findByShortLink(randomString);
    }

    @Test
    void shouldThrowExceptionGetByShortLink() {
        when(linkInfoRepository.findByShortLink(anyString())).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class, () -> linkInfoService.getByShortLink(testLink));
        assertEquals("Link was not found " + testLink, exception.getMessage());
    }

    @Test
    void findByFilter() {
        List<LinkInfo> linkInfoList = Arrays.asList(getData(), getData());

        when(linkInfoRepository.findAll()).thenReturn(linkInfoList);

        List<LinkInfoResponseDto> responseList = linkInfoService.findByFilter();

        assertNotNull(responseList);
        assertEquals(2, responseList.size());
    }

    private CreateLinkInfoRequestDto getCreateLinkInfoRequest() {
        CreateLinkInfoRequestDto request = new CreateLinkInfoRequestDto();
        request.setLink(testLink);
        request.setEndTime(now);
        request.setDescription(description);
        request.setActive(true);
        return request;
    }

    private LinkInfo getData() {
        long openingCount = 0L;
        return LinkInfo.builder()
                .id(UUID.randomUUID())
                .shortLink(randomString)
                .openingCount(openingCount)
                .link(testLink)
                .endTime(now)
                .description(description)
                .active(true)
                .build();
    }


}