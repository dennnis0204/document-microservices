package com.coders.documentservice.utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Hex;

import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.security.auth.x500.X500Principal;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class PdfService {

    private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(PdfService.class);

    public static String getFileNameFromPath(String filePath) {
        Path path = Paths.get(filePath);
        return path.getFileName().toString();
    }

    public static String calculateDigestString(String inputFilePath) {
        byte[] bytes = new byte[0];
        MessageDigest messageDigest = null;
        try {
            bytes = FileUtils.readFileToByteArray(new File(inputFilePath));

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return Hex.getString(Objects.requireNonNull(messageDigest).digest(bytes));

    }

    public static Map<String, String> getCertificateData(String certFilePath) {
        Map<String, String> certMap = new HashMap<>();

        try (InputStream inStream = new FileInputStream(certFilePath)) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
            X500Principal x500Principal = cert.getIssuerX500Principal();
            String dn = x500Principal.getName();
            LdapName ldapDN = new LdapName(dn);
            for (Rdn rdn : ldapDN.getRdns()) {
                certMap.put(rdn.getType(), (String) rdn.getValue());
            }
        } catch (CertificateException | InvalidNameException | IOException e) {
            e.printStackTrace();
        }
        return certMap;
    }

    public static void addPageWithData(
            String inputFilePath,
            String outputFilePath,
            String commonName,
            String organization,
            String fileHash,
            UUID uuid) {
        try (PDDocument pdDocument = PDDocument.load(new File(inputFilePath))) {

            final PDRectangle mediaBox = pdDocument.getPage(0).getMediaBox();

            PDRectangle pdRectangle = new PDRectangle(mediaBox.getWidth(), mediaBox.getHeight());

            PDPage pdPage = new PDPage(pdRectangle);

            pdDocument.addPage(pdPage);

            PDFont font = PDType1Font.HELVETICA;

            // add Common Name, Organization, Hash and UUID to the last page
            try (PDPageContentStream contents = new PDPageContentStream(pdDocument, pdPage)) {
                contents.beginText();
                contents.setFont(font, 10);
                contents.newLineAtOffset(30, mediaBox.getHeight() - 100);
                contents.showText("Common Name : " + commonName);
                contents.newLineAtOffset(0, -20);
                contents.showText("Organization : " + organization);
                contents.newLineAtOffset(0, -20);
                contents.showText("Hash of the input file : " + fileHash);
                contents.newLineAtOffset(0, -20);
                contents.showText("UUID : " + uuid.toString());
                contents.endText();
            }

            String outputFileName = getFileNameFromPath(inputFilePath);
            outputFileName = outputFileName.substring(0, outputFileName.length() - 4) + "_processed.pdf";

            if (outputFilePath.endsWith("/")) {
                outputFilePath = outputFilePath + outputFileName;
            } else {
                outputFilePath = outputFilePath + "/" + outputFileName;
            }
            pdDocument.save(outputFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
