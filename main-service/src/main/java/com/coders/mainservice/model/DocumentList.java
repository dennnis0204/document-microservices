package com.coders.mainservice.model;

import java.util.ArrayList;
import java.util.List;

public class DocumentList {

    private final List<Document> documentList;

    public DocumentList() {
        this.documentList = new ArrayList<Document>();
    }

    public DocumentList(List<Document> documentList) {
        this.documentList = documentList;
    }

    public List<Document> getDocumentList() {
        return documentList;
    }
}
