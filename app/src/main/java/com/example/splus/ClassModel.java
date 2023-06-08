package com.example.splus;

public class ClassModel {
  private final String classId;
  private final String className;
  private final String instructorName;

  public ClassModel(String classId, String className, String instructorName) {
    this.classId = classId;
    this.className = className;
    this.instructorName = instructorName;
  }

  public String getClassId() {
    return classId;
  }

  public String getClassName() {
    return className;
  }

  public String getInstructorName() {
    return instructorName;
  }

}
