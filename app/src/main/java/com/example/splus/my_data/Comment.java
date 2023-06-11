package com.example.splus.my_data;

import java.util.Date;

public class Comment {
    private String id;
    private String ownerDisplayName;
    private String ownerID;
    private String courseID;
    private String text;
    private int likeCount;
    private int dislikeCount;
    private Date createdDate;
    private Boolean isLike;
    private int replyCount;

    public Comment(String id) {
        this.id = id;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean IsLike() {
        return isLike;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setLike(Boolean isLike) {
        this.isLike = isLike;
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

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }
}
