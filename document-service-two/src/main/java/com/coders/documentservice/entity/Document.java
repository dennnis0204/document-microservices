package com.coders.documentservice.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name="file_name")
    private String fileName;

    @Column(name = "digest_string")
    private String digestString;

    @Column(name = "date_and_time")
    private LocalDateTime localDateTime;

    @Column(name = "path")
    private String path;

    @Column(name = "user")
    private String user;

    public Document() {
    }

    public Document(UUID uuid, String fileName, String digestString, LocalDateTime localDateTime, String path, String user) {
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

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", fileName='" + fileName + '\'' +
                ", digestString='" + digestString + '\'' +
                ", localDateTime=" + localDateTime +
                ", path='" + path + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
