package com.coders.documentservice.service;


import com.coders.documentservice.entity.Document;

import java.util.List;

public interface DocumentService {

    List<Document> findAll();

    void save(Document document);

    List<Document> findAllByUsername(String username);
}
