package com.coders.documentservice.service;

import com.coders.documentservice.dao.DocumentDAO;
import com.coders.documentservice.entity.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentDAO documentDAO;

    public DocumentServiceImpl(DocumentDAO documentDAO) {
        this.documentDAO = documentDAO;
    }

    @Override
    public List<Document> findAll() {
        return documentDAO.findAll();
    }

    @Override
    @Transactional
    public void save(Document document) {
        documentDAO.save(document);
    }

    @Override
    @Transactional
    public List<Document> findAllByUsername(String username) {
        return documentDAO.findAllByUsername(username);
    }
}
