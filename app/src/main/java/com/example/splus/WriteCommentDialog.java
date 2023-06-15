package com.example.splus;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class WriteCommentDialog extends DialogFragment {

    private EditText editText_WriteComment;

    private String commentText;

    private boolean canCommit;

    public static WriteCommentDialog newInstance(String commentText) {
        WriteCommentDialog dialog = new WriteCommentDialog();
        dialog.commentText = commentText;
        dialog.setStyle(STYLE_NO_TITLE, android.R.style.Theme_Holo);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_write_comment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getColor(R.color.unfocused_background)));
        editText_WriteComment = view.findViewById(R.id.editText_WriteComment);
        ImageButton buttonCommit = view.findViewById(R.id.button_CommitComment);
        buttonCommit.setOnClickListener(v -> {
            canCommit = true;
            dismiss();
        });
        editText_WriteComment.setSelection(editText_WriteComment.getText().length());
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        commentText = editText_WriteComment.getText().toString();
        super.onDismiss(dialog);
    }
}