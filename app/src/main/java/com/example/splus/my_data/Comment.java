package com.example.splus.my_data;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Comment {
    private String id;
    private String ownerDisplayName;
    private String ownerID;
    private String courseID;
    private String text;
    private int likeCount;
    private int dislikeCount;
    private Timestamp createdDate;
    private Boolean isLike;

    private int replyCount;

    public Comment() {
        // Default constructor for Firebase Firestore
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerDisplayName() {
        return ownerDisplayName;
    }

    public void setOwnerDisplayName(String ownerDisplayName) {
        this.ownerDisplayName = ownerDisplayName;
    }

    public String getCourseId() {
        return courseID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean isLike() {
        return isLike;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setLike(Boolean isLike) {
        this.isLike = isLike;
    }

    public void like() {
        likeCount++;
        isLike = true;
    }

    public void dislike() {
        dislikeCount++;
        isLike = false;
    }

    public void unlike() {
        likeCount--;
        isLike = null;
    }

    public void undislike() {
        dislikeCount--;
        isLike = null;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }
}
