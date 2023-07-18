package com.example.splus.my_data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Course implements Parcelable {
    private String courseId;
    private String courseName;
    private String creatorName;
    private int studentCount;
    private Date creationTime;

    public Course() {
        // Empty constructor for Firebase Firestore
    }

    public Course(String courseId, String courseName, String creatorName, int studentCount) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.creatorName = creatorName;
        this.studentCount = studentCount;
        this.creationTime = new Date(); // Set current time as creation time
    }

    protected Course(Parcel in) {
        courseId = in.readString();
        courseName = in.readString();
        creatorName = in.readString();
        creationTime = new Date(in.readLong());
        studentCount = in.readInt();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
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

    public Date getCreationTime() {
        return creationTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(courseId);
        dest.writeString(courseName);
        dest.writeString(creatorName);
        dest.writeLong(creationTime.getTime());
        dest.writeInt(studentCount);
    }

}
