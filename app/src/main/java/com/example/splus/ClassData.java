package com.example.splus;

public class ClassData extends ItemData {
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getClassSize() {
        return classSize;
    }

    public void setClassSize(int classSize) {
        this.classSize = classSize;
    }

    private String teacherName;
    private int classSize;

    public ClassData(int resourceID, String itemID, String itemName, String itemDes) {
        super(resourceID, itemID, itemName, itemDes);
    }

    public ClassData(ItemData item, String teacherName, int classSize) {
        super(item.getResourceID(), item.getItemID(), item.getItemName(), item.getItemDes());
        this.teacherName = teacherName;
        this.classSize = classSize;
    }
}
