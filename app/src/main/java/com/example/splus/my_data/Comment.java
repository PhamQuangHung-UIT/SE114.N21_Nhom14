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
    private String replyCommentId;
    private String parentCommentId;
    private String ownerDisplayName;
    private String ownerId;
    private String text;
    private int likeCount;
    private int dislikeCount;
    @ServerTimestamp
    private Timestamp createdDate;
    @Exclude
    private Boolean isLike;

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

    public Boolean isLike() {
        return isLike;
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

    public String getReplyCommentId() {
        return replyCommentId;
    }

    public void setReplyCommentId(String replyCommentId) {
        this.replyCommentId = replyCommentId;
    }

    public String getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return likeCount == comment.likeCount && dislikeCount == comment.dislikeCount
                && replyCount == comment.replyCount && Objects.equals(courseId, comment.courseId)
                && Objects.equals(replyCommentId, comment.replyCommentId)
                && Objects.equals(parentCommentId, comment.parentCommentId)
                && Objects.equals(ownerDisplayName, comment.ownerDisplayName)
                && Objects.equals(ownerId, comment.ownerId)
                && Objects.equals(text, comment.text)
                && Objects.equals(createdDate, comment.createdDate)
                && Objects.equals(isLike, comment.isLike);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, replyCommentId, parentCommentId, ownerDisplayName, ownerId, text, likeCount, dislikeCount, createdDate, isLike, replyCount);
    }
}
