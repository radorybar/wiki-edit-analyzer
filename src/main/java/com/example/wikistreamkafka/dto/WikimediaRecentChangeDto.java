package com.example.wikistreamkafka.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WikimediaRecentChangeDto {

    @JsonProperty("$schema")
    private String schema;

    private Meta meta;

    // Common fields
    private Long id;
    private String type;
    private Integer namespace;
    private String title;
    private String comment;
    private Long timestamp;
    private String user;
    private Boolean bot;
    private Boolean minor;
    private Boolean patrolled;

    @JsonProperty("server_url")
    private String serverUrl;

    @JsonProperty("server_name")
    private String serverName;

    @JsonProperty("server_script_path")
    private String serverScriptPath;

    private String wiki;

    @JsonProperty("parsedcomment")
    private String parsedComment;

    // Fields for 'edit', 'new'
    private Length length;
    private Revision revision;

    // Fields from the root of the schema
    private String database;

    @JsonProperty("page_title")
    private String pageTitle;

    @JsonProperty("page_url")
    private String pageUrl;

    private Performer performer;

    @JsonProperty("rev_id")
    private Long revId;

    @JsonProperty("rev_timestamp")
    private String revTimestamp;

    @JsonProperty("rev_sha1")
    private String revSha1;

    @JsonProperty("rev_minor_edit")
    private Boolean revMinorEdit;

    @JsonProperty("rev_len")
    private Long revLen;

    @JsonProperty("rev_parent_id")
    private Long revParentId;

    @JsonProperty("rev_content_model")
    private String revContentModel;

    @JsonProperty("rev_content_format")
    private String revContentFormat;

    // Fields for 'log'
    @JsonProperty("log_id")
    private Long logId;

    @JsonProperty("log_type")
    private String logType;

    @JsonProperty("log_action")
    private String logAction;

    @JsonProperty("log_params")
    private Map<String, Object> logParams;

    @JsonProperty("log_action_comment")
    private String logActionComment;


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Meta {
        private String uri;
        @JsonProperty("request_id")
        private String requestId;
        private String id;
        private String dt;
        private String domain;
        private String stream;
        private String topic;
        private Integer partition;
        private Long offset;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Length {
        private Long old;
        @JsonProperty("new")
        private Long newLength;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Revision {
        private Long old;
        @JsonProperty("new")
        private Long newRevision;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Performer {
        @JsonProperty("user_text")
        private String userText;
        @JsonProperty("user_groups")
        private List<String> userGroups;
        @JsonProperty("user_is_bot")
        private Boolean userIsBot;
        @JsonProperty("user_edit_count")
        private Integer userEditCount;
        @JsonProperty("user_id")
        private Long userId;
        @JsonProperty("user_registration_dt")
        private String userRegistrationDt;
    }
}
