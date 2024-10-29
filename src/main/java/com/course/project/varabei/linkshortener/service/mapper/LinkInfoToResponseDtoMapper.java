package com.course.project.varabei.linkshortener.service.mapper;

import com.course.project.varabei.linkshortener.dao.dto.response.LinkInfoResponseDto;
import com.course.project.varabei.linkshortener.dao.model.LinkInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LinkInfoToResponseDtoMapper {
    LinkInfoResponseDto mapToResponseDto(LinkInfo linkInfo);
}
