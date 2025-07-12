package com.example.academiahub.repository;

import com.example.academiahub.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    // Custom query methods (if needed)
}
