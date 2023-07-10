package com.example.splus.my_data;

import java.io.Serializable;

public class Course implements Serializable {
    private String courseId;
    private String courseName;
    private String creatorName;
    private int studentCount;
    private String creationTime;
    private String courseDescription;

    public Course() {
        // Default constructor for Firebase Firestore
    }

    public Course(String courseId, String courseName, String courseTeacherName, String creationTime, String courseDescription) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.creatorName = courseTeacherName;
        this.creationTime = creationTime;
        this.courseDescription = courseDescription;
    }

    // Getters and setters for the attributes

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }
}
