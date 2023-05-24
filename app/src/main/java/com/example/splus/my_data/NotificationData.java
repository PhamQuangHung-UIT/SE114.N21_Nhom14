package com.example.splus.my_data;

import java.io.Serializable;

public class NotificationData implements Serializable {
    private int id;
    private int resourceId;     // image
    private String title;
    private String content;
    private String timestampt;
    private int type;       // 0: other, 1: lesson, 2: homework, 3: hw result, 4: teacher response
    private int sourceId;   // id of a broadcast, or lesson, homework, submission, comment

    public NotificationData(int id, String title, String content, String timestampt, int resourceId, int type, int sourceId) {
        this.id = id;
        this.resourceId = resourceId;
        this.title = title;
        this.content = content;
        this.timestampt = timestampt;
        this.type = type;
        this.sourceId = sourceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestampt() {
        return timestampt;
    }

    public void setTimestampt(String timestampt) {
        this.timestampt = timestampt;
    }

    public boolean isStudyNotification() {
        return this.type != 0;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }
}
