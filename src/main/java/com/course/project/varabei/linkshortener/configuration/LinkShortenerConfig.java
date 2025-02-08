package com.course.project.varabei.linkshortener.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@EnableAsync
@Configuration
@EnableScheduling
public class LinkShortenerConfig {

    @Bean
    public ExecutorService removeOldLinkInfoExecutor() {
        return new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.DiscardPolicy()
        );
    }

    @Bean
    public String notFoundPage() throws IOException {
        try (InputStream is = new ClassPathResource("templates/404.html").getInputStream()) {
            byte[] binaryData = FileCopyUtils.copyToByteArray(is);
            return new String(binaryData);
        }
    }
}
