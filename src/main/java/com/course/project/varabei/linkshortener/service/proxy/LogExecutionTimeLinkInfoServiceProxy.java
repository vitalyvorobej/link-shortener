package com.course.project.varabei.linkshortener.service.proxy;

import com.course.project.varabei.linkshortener.dao.dto.request.CreateLinkInfoRequestDto;
import com.course.project.varabei.linkshortener.dao.dto.request.UpdateLinkInfoRequestDto;
import com.course.project.varabei.linkshortener.dao.dto.response.LinkInfoResponseDto;
import com.course.project.varabei.linkshortener.service.LinkInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@Slf4j
public class LogExecutionTimeLinkInfoServiceProxy implements LinkInfoService {

    private final LinkInfoService linkInfoService;

    public LogExecutionTimeLinkInfoServiceProxy(LinkInfoService linkInfoService) {
        this.linkInfoService = linkInfoService;
    }

    @Override
    public LinkInfoResponseDto createLinkInfo(CreateLinkInfoRequestDto request) {
        return startLogging(() -> linkInfoService.createLinkInfo(request));

    }

    @Override
    public LinkInfoResponseDto getByShortLink(String shortLink) {
        return startLogging(() -> linkInfoService.getByShortLink(shortLink));
    }

    @Override
    public List<LinkInfoResponseDto> findByFilter() {
        return startLogging(linkInfoService::findByFilter);
    }

    @Override
    public void deleteShortLinkById(UUID shortLinkId) {
        linkInfoService.deleteShortLinkById(shortLinkId);
    }

    @Override
    public LinkInfoResponseDto updateLinkInfo(UpdateLinkInfoRequestDto request) {
        return startLogging(() -> linkInfoService.updateLinkInfo(request));
    }

    private <T> T startLogging(Supplier<T> supplier) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("Execution time started {}", stopWatch.getTotalTimeNanos());
        try {
            return supplier.get();
        } finally {
            stopWatch.stop();
            log.info("Execution time ended {}", stopWatch.getTotalTimeNanos());
        }
    }
}
