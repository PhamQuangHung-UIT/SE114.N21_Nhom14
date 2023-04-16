package com.example.splus.my_data;

import java.io.Serializable;

public class ClassData implements Serializable {
    private int lessonImage;
    private String lessonID;
    private String lessonName;
    private String lessonContent;
    private String className;
    private String teacherName;

    public ClassData(int lessonImage, String lessonID, String lessonName, String lessonContent, String className, String teacherName) {
        this.lessonImage = lessonImage;
        this.lessonID = lessonID;
        this.lessonName = lessonName;
        this.lessonContent = lessonContent;
        this.className = className;
        this.teacherName = teacherName;
    }

    public int getLessonImage() {
        return lessonImage;
    }

    public void setLessonImage(int lessonImage) {
        this.lessonImage = lessonImage;
    }

    public String getLessonID() {
        return lessonID;
    }

    public void setLessonID(String lessonID) {
        this.lessonID = lessonID;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getLessonContent() {
        return lessonContent;
    }

    public void setLessonContent(String lessonContent) {
        this.lessonContent = lessonContent;
    }
}
