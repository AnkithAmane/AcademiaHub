package com.example.academiahub.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileStorageUtil {
    private static final String CERTIFICATES_DIR = "certificates";

    public String saveCertificateFile(MultipartFile file) {
        // Validate PDF by MIME type and extension
        String contentType = file.getContentType();
        String originalFilename = file.getOriginalFilename();
        if (contentType == null || !contentType.equalsIgnoreCase("application/pdf") ||
                originalFilename == null || !originalFilename.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("Only PDF files are allowed.");
        }
        try {
            Files.createDirectories(Paths.get(CERTIFICATES_DIR));
            String uniqueFileName = UUID.randomUUID() + "_" + originalFilename;
            Path filePath = Paths.get(CERTIFICATES_DIR, uniqueFileName);
            file.transferTo(filePath.toFile());
            return uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store certificate file", e);
        }
    }
}
