package com.course.project.varabei.linkshortener.dao.mapper.response;

import com.course.project.varabei.linkshortener.dao.dto.request.UpdateLinkInfoRequestDto;
import com.course.project.varabei.linkshortener.dao.dto.response.LinkInfoResponseDto;
import com.course.project.varabei.linkshortener.dao.model.LinkInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface UpdateLinkInfoDaoResponseMapper {

    @Mapping(target = "shortLink", ignore = true)
    @Mapping(target = "openingCount", ignore = true)
    LinkInfoResponseDto mapToResponse(UpdateLinkInfoRequestDto requestDto);

    @Mapping(target = "shortLink", ignore = true)
    @Mapping(target = "openingCount", ignore = true)
    void updateLinkInfoFromDto(UpdateLinkInfoRequestDto updateDto, @MappingTarget LinkInfo linkInfo);
}
