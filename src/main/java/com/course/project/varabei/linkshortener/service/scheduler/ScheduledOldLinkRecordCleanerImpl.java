package com.course.project.varabei.linkshortener.service.scheduler;

import com.course.project.varabei.linkshortener.dao.repository.LinkInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ScheduledOldLinkRecordCleanerImpl implements ScheduledOldLinkRecordCleaner {

    private final LinkInfoRepository linkInfoRepository;

    @Async("removeOldLinkInfoExecutor")
    @Scheduled(cron = "#{@environment.getProperty('link-shortener.remove-old-link-infos.cron')}")
    @Override
    public void cleanExpiredLink() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusMonths(1);
        linkInfoRepository.deleteExpiredLinks(cutoffDate);
    }
}