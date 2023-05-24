package com.example.splus.my_data;

import android.content.res.Resources;

import com.example.splus.R;

import java.io.Serializable;

public class HomeworkData implements Serializable {
    private int id;
    private String homework_name;
    private String time;
    private String deadline;
    private String class_id;
    private String class_name;
    private int format;   // 0: trac nghiem, 1: tu luan dien khuyet, 2: tu luan trinh bay
    // private JSONOBJ content;
    private double point;   // not include point in homework table, get in submission table based on task_id

    private boolean finished;

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public HomeworkData(int id, String homework_name, String time, String deadline, String class_id, String class_name, int format, double point, boolean finished) {
        this.id = id;
        this.homework_name = homework_name;
        this.time = time;
        this.deadline = deadline;
        this.class_id = class_id;
        this.class_name = class_name;
        this.format = format;
        this.point = point;
        this.finished = finished;
    }

    public HomeworkData(boolean finished) {
        this.id = 1;
        this.homework_name = "Bài kiểm tra Chương 1";
        this.time = "30 phút";
        this.deadline = "00:00 25/05/2023";
        this.class_id = "SPLUS-MATHEMATICS";
        this.class_name = "Nhập môn Toán học";
        this.format = 0;
        this.point = 9.8;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHomework_name() {
        return homework_name;
    }

    public void setHomework_name(String homework_name) {
        this.homework_name = homework_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public boolean isFinished() {
        return this.finished;
        // check if (task_id, acc_id) match a record in submission table
    }


}
