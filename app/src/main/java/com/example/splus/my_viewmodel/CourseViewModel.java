package com.example.splus.my_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.splus.my_data.Course;

public class CourseViewModel extends ViewModel {
    private final MutableLiveData<Course> currentCourse = new MutableLiveData<>();

    private final MutableLiveData<String> parentCommentId = new MutableLiveData<>();

    private final MutableLiveData<String> commentText = new MutableLiveData<>();

    private final MutableLiveData<String> replyToOwnerName = new MutableLiveData<>();


    private final MutableLiveData<String> editCommentId = new MutableLiveData<>();


    public Course getCurrentCourse() {
        return currentCourse.getValue();
    }

    public void setCurrentCourse(Course newCourse) {
        currentCourse.setValue(newCourse);
    }

    public String getParentCommentId() {
        return parentCommentId.getValue();
    }

    public void setParentCommentId(String newCommendId) {
        parentCommentId.setValue(newCommendId);
    }

    public MutableLiveData<String> getCommentText() {
        return commentText;
    }

    public MutableLiveData<String> getReplyToOwnerName() {
        return replyToOwnerName;
    }

    public String getEditCommentId() {
        return editCommentId.getValue();
    }

    public void setEditCommentId(String newCommentId) {
        editCommentId.setValue(newCommentId);
    }
}
