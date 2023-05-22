package com.example.splus.my_data;

import java.io.Serializable;

public class ClassData implements Serializable {
    private int classImage;
    private String classID;
    private String className;
    private String teacherName;

    public ClassData(int classImage, String classID, String className, String teacherName) {
        this.classImage = classImage;
        this.classID = classID;
        this.className = className;
        this.teacherName = teacherName;
    }

    public int getClassImage() {
        return classImage;
    }

    public void setClassImage(int classImage) {
        this.classImage = classImage;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
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
}
