package com.example.splus.my_data;

import java.util.Date;

public class Comment {
    private String id;
    private String ownerId;
    private String courseID;
    private String text;
    private int likeCount;
    private int dislikeCount;
    private Date createdDate;
    private Boolean isLike;
    private int repliesCount;

    public Comment(String id, String ownerId, String courseID) {
        this.id = id;
        this.ownerId = ownerId;
        this.courseID = courseID;
    }

    public String getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getCourseId() {return courseID;}

    public String getText() {
        return text;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public int getRepliesCount() {
        return repliesCount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Boolean IsLike() {
        return isLike;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public void setLike(Boolean isLike) {
        this.isLike = isLike;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setRepliesCount(int repliesCount) {
        this.repliesCount = repliesCount;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void Like() {
        likeCount++;
        dislikeCount--;
        isLike = true;
    }

    public void Dislike() {
        dislikeCount++;
        likeCount--;
        isLike = false;
    }

    public void Unlike() {
        likeCount--;
        isLike = null;
    }

    public void Undislike() {
        dislikeCount--;
        isLike = null;
    }
}
