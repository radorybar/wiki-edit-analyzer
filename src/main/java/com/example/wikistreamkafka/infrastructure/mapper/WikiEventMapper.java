package com.example.wikistreamkafka.infrastructure.mapper;

import com.example.wikistreamkafka.api.dto.WikimediaRecentChangeDto;
import com.example.wikistreamkafka.domain.model.WikiEvent;
import org.springframework.stereotype.Component;

/**
 * Mapper service responsible for converting between DTOs and domain models.
 * This service helps maintain a clear separation between external data representation
 * and internal domain models.
 */
@Component
public class WikiEventMapper {

    /**
     * Converts a WikimediaRecentChangeDto to a WikiEvent domain model.
     *
     * @param dto The DTO object to convert
     * @return A WikiEvent domain model populated with data from the DTO
     */
    public WikiEvent toWikiEvent(WikimediaRecentChangeDto dto) {
        if (dto == null) {
            return null;
        }

        WikiEvent event = new WikiEvent();
        event.setId(dto.getId() != null ? dto.getId().toString() : null);
        event.setType(dto.getType());
        event.setTitle(dto.getTitle());
        event.setUser(dto.getUser());
        event.setTimestamp(dto.getTimestamp());
        event.setComment(dto.getComment());
        event.setServer_url(dto.getServerUrl());
        event.setWiki(dto.getWiki());
        event.setBot(dto.getBot());

        if (dto.getMeta() != null) {
            WikiEvent.Meta meta = new WikiEvent.Meta();
            meta.setUri(dto.getMeta().getUri());
            meta.setDomain(dto.getMeta().getDomain());
            meta.setDt(dto.getMeta().getDt());
            meta.setStream(dto.getMeta().getStream());
            meta.setTopic(dto.getMeta().getTopic());
            event.setMeta(meta);
        }

        return event;
    }

    /**
     * Converts a WikiEvent domain model to a WikimediaRecentChangeDto.
     * This method is provided for completeness but may not be used as frequently
     * as the toWikiEvent method.
     *
     * @param event The domain model to convert
     * @return A WikimediaRecentChangeDto populated with data from the domain model
     */
    public WikimediaRecentChangeDto toDto(WikiEvent event) {
        if (event == null) {
            return null;
        }

        WikimediaRecentChangeDto dto = new WikimediaRecentChangeDto();
        dto.setId(event.getId() != null ? Long.parseLong(event.getId()) : null);
        dto.setType(event.getType());
        dto.setTitle(event.getTitle());
        dto.setUser(event.getUser());
        dto.setTimestamp(event.getTimestamp());
        dto.setComment(event.getComment());
        dto.setServerUrl(event.getServer_url());
        dto.setWiki(event.getWiki());
        dto.setBot(event.getBot());

        if (event.getMeta() != null) {
            WikimediaRecentChangeDto.Meta meta = new WikimediaRecentChangeDto.Meta();
            meta.setUri(event.getMeta().getUri());
            meta.setDomain(event.getMeta().getDomain());
            meta.setDt(event.getMeta().getDt());
            meta.setStream(event.getMeta().getStream());
            meta.setTopic(event.getMeta().getTopic());
            dto.setMeta(meta);
        }

        return dto;
    }
}
