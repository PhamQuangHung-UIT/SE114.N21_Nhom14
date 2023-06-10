package com.example.splus.my_data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
    private final int courseID;
    private final String courseName;
    private final String teacherName;

    public Course(int courseID, String courseName, String teacherName) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.teacherName = teacherName;
    }

    public List<Account> getListStudent() {
        // Query SELECT * FROM enrollment WHERE course_id= :courseID
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account(
                "1",
                "Nguyen Van A",
                0
        ));
        accountList.add(new Account(
                "2",
                "Nguyen Van B",
                0
        ));
        accountList.add(new Account(
                "3",
                "Nguyen Van C",
                0
        ));
        accountList.add(new Account(
                "4",
                "Nguyen Van D",
                0
        ));
        return accountList;
    }

    public List<Lesson> getListLesson() {
        // Query SELECT * FROM lesson WHERE course_id= :courseID
        List<Lesson> lessonList = new ArrayList<>();
        lessonList.add(new Lesson(
                0,
                "Lesson 1",
                "Content",
                "Course name",
                "Teacher name"
        ));
        lessonList.add(new Lesson(
                0,
                "Lesson 2",
                "Content",
                "Course name",
                "Teacher name"
        ));
        lessonList.add(new Lesson(
                0,
                "Lesson 3",
                "Content",
                "Course name",
                "Teacher name"
        ));
        lessonList.add(new Lesson(
                0,
                "Lesson 4",
                "Content",
                "Course name",
                "Teacher name"
        ));
        return lessonList;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }
}
