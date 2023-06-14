package com.example.splus.my_data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Submission implements Serializable {
    private final String student_id;
    private final String assignment_id;
    private String result;
    private final String details;
    private final String timestamp;
    public List<Integer> listSelection;

    public Submission(String student_id, String assignment_id, String details, String timestamp) throws JSONException {
        this.student_id = student_id;
        this.assignment_id = assignment_id;
        this.details = details;
        this.timestamp = timestamp;
        setResult();
    }

    private void setResult() throws JSONException {
        listSelection = new ArrayList<>();
        JSONObject result = new JSONObject(details);
        JSONArray content = result.getJSONObject("content").getJSONArray("submission");
        int correct= 0;
        for (int index=0; index<content.length(); index++) {
            if (content.getJSONObject(index).getString("status").equals("true")) {
                correct = correct + 1;
            }
            switch (content.getJSONObject(index).getString("selection")) {
                case "a":
                    listSelection.add(0);
                    break;
                case "b":
                    listSelection.add(1);
                    break;
                case "c":
                    listSelection.add(2);
                    break;
                default:
                    listSelection.add(3);
            }
        }
        this.result = correct + "/" + content.length();
    }

    public String getStudent_id() {
        return student_id;
    }

    public String getAssignment_id() {
        return assignment_id;
    }

    public String getDetails() {
        return details;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getResult() {
        return result;
    }
}
