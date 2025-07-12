package com.example.academiahub.dto;

public class CertificateDTO {
    private Long courseRequestId;
    private String fileName;

    public CertificateDTO() {
    }

    public CertificateDTO(Long courseRequestId, String fileName) {
        this.courseRequestId = courseRequestId;
        this.fileName = fileName;
    }

    public Long getCourseRequestId() {
        return courseRequestId;
    }

    public void setCourseRequestId(Long courseRequestId) {
        this.courseRequestId = courseRequestId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
