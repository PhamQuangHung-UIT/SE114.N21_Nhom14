package com.example.splus.my_interface;

import com.example.splus.my_data.Assignment;

import org.json.JSONException;

public interface IClickAssignmentListener {
    void onClickAssignment(Assignment assignment) throws JSONException;
}
