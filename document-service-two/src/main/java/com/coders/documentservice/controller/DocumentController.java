package com.coders.documentservice.controller;

import com.coders.documentservice.entity.Document;
import com.coders.documentservice.model.DocumentList;
import com.coders.documentservice.model.Validator;
import com.coders.documentservice.service.DocumentService;
import com.coders.documentservice.utils.PdfService;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping
public class DocumentController {

    private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(DocumentController.class);

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/document")
    public Validator mainApi(@RequestParam String certificate,
                             @RequestParam String document,
                             @RequestParam String outputs,
                             @RequestParam String username) {

        Validator validator = new Validator();

        Map<String, Boolean> validationMap = validator.getValidationMap();

        File cer = new File(certificate);
        if (!cer.exists() || !cer.isFile() || !certificate.endsWith(".cer") || certificate.length() < 5) {
            validationMap.put("wrong_certificate", Boolean.TRUE);
            log.warn("Certificate doesn't exist or wrong certificate file path : " + certificate);
        }

        long fileSize = 1_000_000L;

        File doc = new File(document);
        if (!doc.exists() || !doc.isFile() || !document.endsWith(".pdf") || document.length() < 5) {
            validationMap.put("wrong_document", Boolean.TRUE);
            log.warn("Document doesn't exist or wrong document file path : " + document);
        } else {
            try {
                fileSize = FileUtils.sizeOf(doc);
            } catch (IllegalArgumentException e) {
                validationMap.put("document_doesnt_exist", Boolean.TRUE);
                log.warn("Document doesn't exist : " + document);
            }
        }

        if (fileSize < 1_000_000L || fileSize > 20_000_000L) {
            validationMap.put("wrong_document_size", Boolean.TRUE);
            log.warn("Document size isn't match requirements, file size is : " + fileSize + " bytes");
        }

        if (!Files.exists(Path.of(outputs)) || outputs.equals("")) {
            validationMap.put("wrong_output_path", Boolean.TRUE);
            log.warn("Wrong output path : " + outputs);
        }

        if (validationMap.size() > 0) {
            return validator;
        }

        log.info("Input fields validation passed");

        UUID uuid = UUID.randomUUID();

        Map<String, String> certificateData = PdfService.getCertificateData(certificate);
        String digestString = PdfService.calculateDigestString(document);
        PdfService.addPageWithData(
                document,
                outputs,
                certificateData.get("CN"),
                certificateData.get("O"),
                digestString,
                uuid
        );

        String fileName = PdfService.getFileNameFromPath(document);

        Document newDocument = new Document(uuid, fileName, digestString, LocalDateTime.now(), outputs, username);
        documentService.save(newDocument);
        log.info("Saved new document : " + newDocument);

        return validator;
    }

    @GetMapping("/all-records")
    public DocumentList allRecords(@RequestParam String username) {
        List<Document> allRecords = documentService.findAllByUsername(username);
        DocumentList documentList = new DocumentList(allRecords);
        return documentList;
    }
}
