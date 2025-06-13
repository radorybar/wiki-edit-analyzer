package com.example.wikistreamkafka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WikiEvent {
    private String id;
    private String type;
    private String title;
    private String user;
    private Long timestamp;
    private String comment;
    private String server_url;
    private String wiki;
    private Boolean bot;
    private Meta meta;
    
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Meta {
        private String uri;
        private String domain;
        private String dt;
        private String stream;
        private String topic;
    }
}
