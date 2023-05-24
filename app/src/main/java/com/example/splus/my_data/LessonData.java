package com.example.splus.my_data;

import com.example.splus.R;

import java.io.Serializable;

public class LessonData implements Serializable {
    private int lessonImage;
    private String lessonID;
    private String lessonName;
    private String lessonContent;
    private String className;
    private String teacherName;

    public LessonData(int lessonImage, String lessonID, String lessonName, String lessonContent, String className, String teacherName) {
        this.lessonImage = lessonImage;
        this.lessonID = lessonID;
        this.lessonName = lessonName;
        this.lessonContent = lessonContent;
        this.className = className;
        this.teacherName = teacherName;
    }

    public LessonData() {
        this.lessonImage = R.drawable.splus_logo;
        this.lessonID = "SPLUS-MATHEMATICS";
        this.lessonName = "Tầm quan trọng của Toán học";
        this.lessonContent = "Nội dung bài học";
        this.className = "Nhập môn Toán học";
        this.teacherName = "S-Plus";
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
