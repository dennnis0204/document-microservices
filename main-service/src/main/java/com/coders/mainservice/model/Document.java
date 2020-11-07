package com.coders.mainservice.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Document {

    private Long id;

    private UUID uuid;

    private String fileName;

    private String digestString;

    private LocalDateTime localDateTime;

    private String path;

    private String user;

    public Document() {
    }

    public Document(Long id, UUID uuid, String fileName, String digestString, LocalDateTime localDateTime, String path, String user) {
        this.id = id;
        this.uuid = uuid;
        this.fileName = fileName;
        this.digestString = digestString;
        this.localDateTime = localDateTime;
        this.path = path;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDigestString() {
        return digestString;
    }

    public void setDigestString(String digestString) {
        this.digestString = digestString;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
