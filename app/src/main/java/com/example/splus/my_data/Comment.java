package com.example.splus.my_data;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Objects;

public class Comment {
    @DocumentId
    private String id;
    private String courseId;
    private String replyOwnerId;
    private String parentCommentId;
    private String ownerDisplayName;
    private String replyOwnerName;
    private String ownerId;
    private String text;
    private int likeCount;
    private int dislikeCount;
    @ServerTimestamp
    private Timestamp createdDate;
    @Exclude
    private Boolean like;

    private int replyCount;

    // Default constructor for Firebase Firestore
    public Comment() {

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

    @Exclude
    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    public void like() {
        likeCount++;
    }

    public void dislike() {
        dislikeCount++;
    }

    public void unlike() {
        likeCount--;
    }

    public void undislike() {
        dislikeCount--;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getReplyOwnerId() {
        return replyOwnerId;
    }

    public void setReplyOwnerId(String replyOwnerId) {
        this.replyOwnerId = replyOwnerId;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public String getReplyOwnerName() {
        return replyOwnerName;
    }

    public void setReplyOwnerName(String replyOwnerName) {
        this.replyOwnerName = replyOwnerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return likeCount == comment.likeCount && dislikeCount == comment.dislikeCount
                && replyCount == comment.replyCount && Objects.equals(courseId, comment.courseId)
                && Objects.equals(replyOwnerId, comment.replyOwnerId)
                && Objects.equals(parentCommentId, comment.parentCommentId)
                && Objects.equals(ownerDisplayName, comment.ownerDisplayName)
                && Objects.equals(ownerId, comment.ownerId)
                && Objects.equals(text, comment.text)
                && Objects.equals(createdDate, comment.createdDate)
                && Objects.equals(like, comment.like);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, replyOwnerId, parentCommentId, ownerDisplayName, ownerId, text, likeCount, dislikeCount, createdDate, like, replyCount);
    }
}
