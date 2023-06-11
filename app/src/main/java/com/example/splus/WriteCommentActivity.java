package com.example.splus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class WriteCommentActivity extends AppCompatActivity {

    private EditText editText_WriteComment;

    private boolean canCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_comment);
        editText_WriteComment = findViewById(R.id.editText_WriteComment);
        ImageButton buttonCommit = findViewById(R.id.button_CommitComment);
        buttonCommit.setOnClickListener(view -> {
            canCommit = true;
            finish();
        });
        editText_WriteComment.setSelection(editText_WriteComment.getText().length());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent();
        intent.putExtra("commentString", editText_WriteComment.getText().toString());
        setResult(canCommit ? RESULT_OK : RESULT_CANCELED, intent);
    }
}