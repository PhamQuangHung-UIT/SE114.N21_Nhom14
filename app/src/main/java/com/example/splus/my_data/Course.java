package com.example.splus.my_data;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseId;
    private String courseName;
    private String createrName;
    private int studentCount;
    private String creationTime;

    public List<Lesson> listLesson = new ArrayList<>();

    public void setListLesson(List<Lesson> list) {
        this.listLesson = list;
    }

    public Course() {
        // Default constructor for Firebase Firestore
    }

    public Course(String courseId, String courseName, String courseTeacherName, String creationTime, int studentCount) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.createrName = courseTeacherName;
        this.creationTime = creationTime;
        this.studentCount = studentCount;

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

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
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

}
