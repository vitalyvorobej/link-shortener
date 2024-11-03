package com.course.project.varabei.linkshortener.service.mapper.request;

import com.course.project.varabei.linkshortener.dao.dto.request.CreateLinkInfoRequestDto;
import com.course.project.varabei.linkshortener.dao.dto.response.LinkInfoResponseDto;
import com.course.project.varabei.linkshortener.dao.model.LinkInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LinkInfoFromRequestDtoMapper {

    @Mapping(target = "openingCount", constant = "0L")
    @Mapping(target = "id", ignore = true)
    LinkInfo fromRequestDto(CreateLinkInfoRequestDto requestDto, String shortLink);

    LinkInfoResponseDto mapToResponseDto(LinkInfo linkInfo);
}
