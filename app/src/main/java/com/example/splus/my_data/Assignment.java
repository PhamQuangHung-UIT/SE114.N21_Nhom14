package com.example.splus.my_data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Timestamp;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

public class Assignment implements Serializable {
    private static int TIMEZONE = 7;
    private final String id;
    private final String name;
    private final String details;
    private int quantity;
    private int format;   // 0: multiple-choice, 1: essay
    private int minutes;
    private String deadline;
    private int showAnswer;       // 0: no, 1: yes
    private final String lessonId;
    private final String courseName;
    private boolean status;

    public static final int MULTIPLE_CHOICE = 0;
    public static final int ESSAY = 1;

    public static final boolean SUBMITTED = true;
    public static final boolean UNSUBMITTED = false;

    public Assignment(String id, String name, String details, String lessonId, String courseName) throws JSONException {
        this.id = id;
        this.name = name;
        this.details = details;
        this.lessonId = lessonId;
        this.courseName = courseName;
        setDetails();
    }

    public Assignment() {
        this.id = "";
        this.name = "Assignment name";
        this.minutes = 90;
        this.deadline = "2023-05-01 00:00:00";
        this.details = "{\"assignment\":{\"name\":\"Final Exam\",\"format\":0,\"mode\":0,\"deadline\":\"2023-06-12 12:12:12\",\"minutes\":90,\"content\":[{\"question\":\"What is the volume of the shape?\",\"a\":\"The size of the shape\",\"b\":\"The weight of the shape\",\"c\":\"The capacity of the shape\",\"d\":\"The circuit of the shape\",\"key\":\"c\"}]}}";
        this.lessonId = "lesson";
        this.courseName = "course name";
        this.format = MULTIPLE_CHOICE;
        this.showAnswer = 0;
        this.status = UNSUBMITTED;
    }

    public String getAssignID() {
        return id;
    }

    public String getAssignName() {
        return name;
    }

    public int getAssignTime() {
        return minutes;
    }

    public String getAssignDeadline() {
        return deadline;
    }

    public String getAssignDetails() {
        return details;
    }

    public String getLessonId() {
        return lessonId;
    }

    public int getAssignFormat() {
        return format;
    }

    public int getShowAnswer() {
        return showAnswer;
    }

    /*
    public Question getQuestion(int number) {

        int index_begin = 0, index_end;
        int counter = 0;
        for (int i=0; i<this.details.length(); i++) {
            if ('#' == this.details.charAt(i)) {
                counter = counter + 1;
            }
            if (number == counter) {
                index_begin = i;
                break;
            }
        }
        index_end = index_begin + 1;
        while (this.details.charAt(index_end) != '#') {
            index_end = index_end + 1;
        }

        String str_details = this.details.substring(index_begin, index_end);

        List<String> items = Arrays.asList(str_details.split("\n"));

        String question = items.get(1);
        String answerA = items.get(2);
        String answerB = items.get(3);
        String answerC = items.get(4);
        String answerD = items.get(5);

        return new Question(number, question, answerA, answerB, answerC, answerD);
    }

     */

    public boolean isExpired() {
        // timestamp example: "2018-09-01 09:01:15"
        Timestamp ts = Timestamp.valueOf(this.deadline);
        Timestamp now = new Timestamp(System.currentTimeMillis() + (1000L * 3600 * TIMEZONE));
        return ts.compareTo(now) < 0;
    }

    public boolean isSubmitted() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setDetails() throws JSONException {
        JSONObject object = new JSONObject(details).getJSONObject("assignment");
        this.format = object.getInt("format");
        this.showAnswer = object.getInt("mode");
        this.deadline = object.getString("deadline");
        this.minutes = object.getInt("minutes");
        this.quantity = object.getJSONArray("content").length();
    }

    public int getQuantity() {
        return this.quantity;
    }

    public int getFormat() {
        return this.format;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public Question getQuestion(int index) throws JSONException {
        String question, answerA, answerB, answerC, answerD;
        char selection, answerkey;
        JSONObject object = new JSONObject(details).getJSONObject("assignment").getJSONArray("content").getJSONObject(index);
        question = object.getString("question");
        answerA = object.getString("a");
        answerB = object.getString("b");
        answerC = object.getString("c");
        answerD = object.getString("d");
        answerkey = object.getString("key").charAt(0);

        return new Question(index, question, answerA, answerB, answerC, answerD, answerkey);
    }

    public String getCourseName() {
        return courseName;
    }
}

