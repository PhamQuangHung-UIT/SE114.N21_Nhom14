package com.example.splus;

import java.io.Serializable;

public class ItemData implements Serializable {
    private int resourceID;
    private String itemID;
    private String itemName;
    private String itemDes;

    public ItemData(int resourceID, String itemID, String itemName, String itemDes) {
        this.resourceID = resourceID;
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemDes = itemDes;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDes() {
        return itemDes;
    }

    public void setItemDes(String itemDes) {
        this.itemDes = itemDes;
    }
}
