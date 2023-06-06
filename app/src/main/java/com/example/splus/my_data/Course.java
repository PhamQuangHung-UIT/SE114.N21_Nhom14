package com.example.splus.my_data;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Course {
    private String courseId;         // ID of the course
    private String courseName;       // Name of the course
    private String courseTeacherName; // Name of the course teacher

    public Course() {
        // Default constructor for Firebase Firestore
    }

    public Course(String courseId, String courseName, String courseTeacherName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseTeacherName = courseTeacherName;
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

    public String getCourseTeacherName() {
        return courseTeacherName;
    }

    public void setCourseTeacherName(String courseTeacherName) {
        this.courseTeacherName = courseTeacherName;
    }
}
