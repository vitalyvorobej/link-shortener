package com.course.project.varabei.linkshortener.service.controller;

import com.course.project.varabei.linkshortener.dao.dto.request.CreateLinkInfoRequestDto;
import com.course.project.varabei.linkshortener.dao.dto.request.UpdateLinkInfoRequestDto;
import com.course.project.varabei.linkshortener.dao.dto.response.LinkInfoResponseDto;
import com.course.project.varabei.linkshortener.service.LinkInfoService;
import com.course.project.varabei.linkshortener.service.dto.request.CommonRequestDto;
import com.course.project.varabei.linkshortener.service.dto.response.CommonResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/link-infos")
@RequiredArgsConstructor
public class LinkInfoController {

    private final LinkInfoService linkInfoService;

    @PostMapping
    public CommonResponseDto<LinkInfoResponseDto> createShortLink(@RequestBody @Valid CommonRequestDto<CreateLinkInfoRequestDto> request) {
        LinkInfoResponseDto linkInfoResponse = linkInfoService.createLinkInfo(request.getBody());
        return CommonResponseDto
                .<LinkInfoResponseDto>builder()
                .id(UUID.randomUUID())
                .body(linkInfoResponse)
                .build();
    }

    @GetMapping
    public CommonResponseDto<List<LinkInfoResponseDto>> getListLinkInfo() {
        List<LinkInfoResponseDto> response = linkInfoService.findByFilter();
        return CommonResponseDto.<List<LinkInfoResponseDto>>builder()
                .id(UUID.randomUUID())
                .body(response)
                .build();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CommonResponseDto<?> deleteShortLinkById(@PathVariable("id") String id) {
        linkInfoService.deleteShortLinkById(UUID.fromString(id));
        return CommonResponseDto.builder()
                .id(UUID.randomUUID())
                .build();
    }

    @PatchMapping
    public CommonResponseDto<LinkInfoResponseDto> updateShortLink(@RequestBody @Valid CommonRequestDto<UpdateLinkInfoRequestDto> request) {
        LinkInfoResponseDto linkInfoResponse = linkInfoService.updateLinkInfo(request.getBody());
        return CommonResponseDto.<LinkInfoResponseDto>builder()
                .id(UUID.randomUUID())
                .body(linkInfoResponse)
                .build();
    }

}
