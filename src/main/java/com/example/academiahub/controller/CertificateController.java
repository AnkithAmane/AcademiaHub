package com.example.academiahub.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.academiahub.model.Certificate;
import com.example.academiahub.service.CertificateService;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {
    private final CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping("/upload")
    public Map<String, Object> uploadCertificate(@RequestParam Long courseRequestId,
            @RequestParam MultipartFile file) {
        Certificate certificate = certificateService.uploadCertificate(courseRequestId, file);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Certificate uploaded successfully");
        response.put("fileName", certificate.getFileName());
        response.put("certificateId", certificate.getId());
        response.put("courseRequestId", courseRequestId);
        return response;
    }
}
