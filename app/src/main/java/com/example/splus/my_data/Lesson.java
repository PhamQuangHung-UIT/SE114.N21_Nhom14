package com.example.splus.my_data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Lesson implements Serializable {
    private int lessonID;
    private String lessonName;
    private String lessonContent;
    private String courseName;
    private String teacherName;

    public Lesson(int lessonID, String lessonName, String lessonContent, String courseName, String teacherName) {
        this.lessonID = lessonID;
        this.lessonName = lessonName;
        this.lessonContent = lessonContent;
        this.courseName = courseName;
        this.teacherName = teacherName;
    }

    public int getLessonID() {
        return lessonID;
    }

    public void setLessonID(int lessonID) {
        this.lessonID = lessonID;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonContent() {
        return lessonContent;
    }

    public void setLessonContent(String lessonContent) {
        this.lessonContent = lessonContent;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public List<Content> retrieveLessonContent() {
        List<Content> contentList = new ArrayList<>();
        // get data from this.lessonContent
        contentList.add(new Content(
                "Tiêu đề 1",
                "Nội dung của tiêu đề 1"
        ));
        contentList.add(new Content(
                "Tiêu đề 2",
                "Nội dung của tiêu đề 2"
        ));
        contentList.add(new Content(
                "Tiêu đề 3",
                "Nội dung của tiêu đề 3"
        ));
        return contentList;
    }

}

class Content {
    private final String heading;
    private final String text;

    public Content(String heading, String text) {
        this.heading = heading;
        this.text = text;
    }

    public String getHeading() {
        return heading;
    }

    public String getText() {
        return text;
    }
}
