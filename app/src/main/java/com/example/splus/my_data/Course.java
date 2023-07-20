package com.example.splus.my_data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Objects;

public class Course implements Parcelable {

    @DocumentId
    private String courseId;
    private String courseName;
    private String creatorId;
    private String creatorName;
    private int studentCount;
    @ServerTimestamp
    private Timestamp creationTime;
    private String courseDescription;

    public Course() {
        // Empty constructor for Firebase Firestore
    }

    public Course(String courseId, String courseName, String courseTeacherName, Timestamp creationTime, String courseDescription) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.creatorName = courseTeacherName;
        this.creationTime = creationTime;
        this.courseDescription = courseDescription;
    }

    protected Course(Parcel in) {
        courseId = in.readString();
        courseName = in.readString();
        creatorId = in.readString();
        creatorName = in.readString();
        studentCount = in.readInt();
        creationTime = in.readParcelable(Timestamp.class.getClassLoader());
        courseDescription = in.readString();
    }

    public static final Creator<Course> CREATOR = new Creator<>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {

        parcel.writeString(courseId);
        parcel.writeString(courseName);
        parcel.writeString(creatorId);
        parcel.writeString(creatorName);
        parcel.writeInt(studentCount);
        parcel.writeParcelable(creationTime, i);
        parcel.writeString(courseDescription);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return studentCount == course.studentCount
                && courseId.equals(course.courseId)
                && Objects.equals(courseName, course.courseName)
                && creatorId.equals(course.creatorId)
                && Objects.equals(creatorName, course.creatorName)
                && Objects.equals(creationTime, course.creationTime)
                && Objects.equals(courseDescription, course.courseDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseName, creatorId, creatorName, studentCount, creationTime, courseDescription);
    }
}
