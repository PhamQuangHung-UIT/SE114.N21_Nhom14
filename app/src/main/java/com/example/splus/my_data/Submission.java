package com.example.splus.my_data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Submission implements Serializable {
    private final String student_id;
    private final String assignment_id;
    private String result;
    private final String details;
    private final String timestampt;

    public Submission(String student_id, String assignment_id, String details, String timestampt) throws JSONException {
        this.student_id = student_id;
        this.assignment_id = assignment_id;
        this.details = details;
        this.timestampt = timestampt;
        setResult();
    }

    private void setResult() throws JSONException {
        JSONObject result = new JSONObject(details);
        JSONArray content = result.getJSONObject("content").getJSONArray("submission");
        int correct= 0;
        for (int index=0; index<content.length(); index++) {
            if (content.getJSONObject(index).getString("status").equals("true")) {
                correct = correct + 1;
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

    public String getTimestampt() {
        return timestampt;
    }

    public String getResult() {
        return result;
    }
}
