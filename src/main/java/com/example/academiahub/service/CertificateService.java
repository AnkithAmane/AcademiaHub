package com.example.academiahub.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.academiahub.model.Certificate;
import com.example.academiahub.model.CourseRequest;
import com.example.academiahub.repository.CertificateRepository;
import com.example.academiahub.repository.CourseRequestRepository;
import com.example.academiahub.util.FileStorageUtil;

@Service
public class CertificateService {
    private final CertificateRepository certificateRepository;
    private final CourseRequestRepository courseRequestRepository;
    private final FileStorageUtil fileStorageUtil;

    @Autowired
    public CertificateService(CertificateRepository certificateRepository,
            CourseRequestRepository courseRequestRepository,
            FileStorageUtil fileStorageUtil) {
        this.certificateRepository = certificateRepository;
        this.courseRequestRepository = courseRequestRepository;
        this.fileStorageUtil = fileStorageUtil;
    }

    public Certificate uploadCertificate(Long requestId, MultipartFile file) {
        CourseRequest courseRequest = courseRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("CourseRequest not found: " + requestId));
        String fileName = fileStorageUtil.saveCertificateFile(file);
        Certificate certificate = new Certificate(fileName, LocalDateTime.now(), courseRequest.getCourseName(),
                courseRequest);
        certificate = certificateRepository.save(certificate);
        courseRequest.setStatus("COMPLETED");
        courseRequestRepository.save(courseRequest);
        return certificate;
    }
}
