package com.example.splus.my_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.splus.my_data.Course;

public class CourseViewModel extends ViewModel {
    private final MutableLiveData<Course> currentCourse = new MutableLiveData<>();

    private final MutableLiveData<String> commentId = new MutableLiveData<>();


    public Course getCurrentCourse() {
        return currentCourse.getValue();
    }

    public void setCurrentCourse(Course newCourse) {
        currentCourse.setValue(newCourse);
    }

    public String getCommentId() {
        return commentId.getValue();
    }

    public void setCommentId(String newCommendId) {
        commentId.setValue(newCommendId);
    }
}
