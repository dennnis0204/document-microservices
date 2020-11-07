package com.coders.documentservice.dao;

import com.coders.documentservice.entity.Document;

import java.util.List;

public interface DocumentDAO {

    List<Document> findAll();

    void save(Document document);

    List<Document> findAllByUsername(String username);
}
