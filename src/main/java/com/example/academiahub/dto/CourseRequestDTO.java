package com.example.academiahub.dto;

public class CourseRequestDTO {
    private String courseName;
    private String courseDuration;
    private String courseProvider;
    private String courseDesc;
    private String justification;
    private Long userId;

    public CourseRequestDTO() {
    }

    public CourseRequestDTO(String courseName, String courseDuration, String courseProvider, String courseDesc,
            String justification, Long userId) {
        this.courseName = courseName;
        this.courseDuration = courseDuration;
        this.courseProvider = courseProvider;
        this.courseDesc = courseDesc;
        this.justification = justification;
        this.userId = userId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }

    public String getCourseProvider() {
        return courseProvider;
    }

    public void setCourseProvider(String courseProvider) {
        this.courseProvider = courseProvider;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
