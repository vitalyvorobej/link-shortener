package com.course.project.varabei.linkshortener.dao.mapper.response;

import com.course.project.varabei.linkshortener.dao.dto.request.UpdateLinkInfoRequestDto;
import com.course.project.varabei.linkshortener.dao.dto.response.LinkInfoResponseDto;
import com.course.project.varabei.linkshortener.dao.model.LinkInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UpdateLinkInfoDaoResponseMapper {

    UpdateLinkInfoDaoResponseMapper INSTANCE = Mappers.getMapper(UpdateLinkInfoDaoResponseMapper.class);

    @Mapping(target = "id", source = "linkInfo.id")
    @Mapping(target = "shortLink", source = "newShortLink")
    @Mapping(target = "link", expression = "java(updateDto.getLink() != null ? updateDto.getLink() : linkInfo.getLink())")
    @Mapping(target = "description", expression = "java(updateDto.getDescription() != null ? updateDto.getDescription() : linkInfo.getDescription())")
    @Mapping(target = "active", expression = "java(updateDto.getActive() != null ? updateDto.getActive() : linkInfo.getActive())")
    @Mapping(target = "endTime", source = "updateDto.endTime")
    LinkInfoResponseDto toResponseDto(LinkInfo linkInfo, UpdateLinkInfoRequestDto updateDto, String newShortLink);

    @Mapping(target = "shortLink", ignore = true)
    @Mapping(target = "openingCount", ignore = true)
    void updateLinkInfoFromDto(UpdateLinkInfoRequestDto updateDto, @MappingTarget LinkInfo linkInfo);
}
