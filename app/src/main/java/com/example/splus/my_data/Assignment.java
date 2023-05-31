package com.example.splus.my_data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

public class Assignment implements Serializable {
    private final int id;
    private final String name;
    private final int quantity;     // number of question
    private final String time;
    private final String deadline;
    private final String content;
    private final int format;   // 0: multiple-choice, 1: essay
    private double result;
    private String submit_time;
    private final int courseID;
    private final String courseName;
    private final int showAnswer;       // 0: no, 1: yes

    public Assignment(int id, String name, int quantity, String time, String deadline, String content, int courseID, String courseName, int format, int showAnswer) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.time = time;
        this.deadline = deadline;
        this.content = content;
        this.format = format;
        this.courseID = courseID;
        this.courseName = courseName;
        this.showAnswer = showAnswer;
    }

    public Assignment() {
        this.id = 0;
        this.name = "Assignment name";
        this.quantity = 10;
        this.time = "1h30m";
        this.deadline = "2023-05-01 00:00:00";
        this.content = "";
        this.courseID = 0;
        this.courseName = "Course name";
        this.format = 0;
        this.showAnswer = 0;
    }

    public int getAssignID() {
        return id;
    }

    public String getAssignName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getAssignTime() {
        return time;
    }

    public String getAssignDeadline() {
        return deadline;
    }

    public String getAssignContent() {
        return content;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getAssignFormat() {
        return format;
    }

    public int getShowAnswer() {
        return showAnswer;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(String submit_time) {
        this.submit_time = submit_time;
    }

    public boolean isFinish() {
        Timestamp timestamp = Timestamp.valueOf(this.deadline);
        Date date = new Date();
        int compare = timestamp.compareTo(date);
        return compare < 0;
    }

    public Question getQuestion(int number) {

        int index_begin = 0, index_end;
        int counter = 0;
        for (int i=0; i<this.content.length(); i++) {
            if ('#' == this.content.charAt(i)) {
                counter = counter + 1;
            }
            if (number == counter) {
                index_begin = i;
                break;
            }
        }
        index_end = index_begin + 1;
        while (this.content.charAt(index_end) != '#') {
            index_end = index_end + 1;
        }

        String str_content = this.content.substring(index_begin, index_end);

        List<String> items = Arrays.asList(str_content.split("\n"));

        String question = items.get(1);
        String answerA = items.get(2);
        String answerB = items.get(3);
        String answerC = items.get(4);
        String answerD = items.get(5);

        return new Question(number, question, answerA, answerB, answerC, answerD);
    }
}

