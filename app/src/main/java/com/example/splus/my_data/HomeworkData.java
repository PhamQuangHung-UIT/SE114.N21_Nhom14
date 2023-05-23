package com.example.splus.my_data;

import java.io.Serializable;

public class HomeworkData implements Serializable {
    private int id;
    private String homework_name;
    private String time;
    private String deadline;
    private String class_id;
    private String class_name;
    private int type;   // 0: trac nghiem, 1: tu luan dien khuyet, 2: tu luan trinh bay
    // private JSONOBJ content;
    private double point;

    public HomeworkData(int id, String homework_name, String time, String deadline, String class_id, String class_name, int type, double point) {
        this.id = id;
        this.homework_name = homework_name;
        this.time = time;
        this.deadline = deadline;
        this.class_id = class_id;
        this.class_name = class_name;
        this.type = type;
        this.point = point;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
}
