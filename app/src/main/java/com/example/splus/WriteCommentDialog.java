package com.example.splus;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.splus.my_firestore_helper.CommentFirestoreHelper;
import com.example.splus.my_viewmodel.CourseViewModel;

public class WriteCommentDialog extends DialogFragment implements MotionLayout.TransitionListener {

    private EditText editText_WriteComment;

    private ImageButton buttonCommit;

    private boolean isCommit;

    private CourseViewModel model;

    private MotionLayout mLayout;

    public static WriteCommentDialog newInstance() {
        WriteCommentDialog dialog = new WriteCommentDialog();
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
        mLayout = getView().findViewById(R.id.motionLayout_writeComment);
        mLayout.addTransitionListener(this);

        model = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
        String replyToOwnerName = model.getReplyToOwnerName().getValue();
        String parentCommentID = model.getParentCommentId();
        String courseID = model.getCurrentCourse().getCourseId();
        String editCommentID = model.getEditCommentId();
        String text = model.getCommentText().getValue();

        editText_WriteComment = view.findViewById(R.id.editText_WriteComment);
        buttonCommit = view.findViewById(R.id.button_CommitComment);
        buttonCommit.setOnClickListener(v -> {
            if (editCommentID == null)
                CommentFirestoreHelper.getInstance().addNewComment(courseID, parentCommentID, parentCommentID,text);
            else CommentFirestoreHelper.getInstance().edit(editCommentID, text);
        });
        if (replyToOwnerName != null) {
            editText_WriteComment.setText(getString(R.string.tagOwnerName, replyToOwnerName));
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.BLUE);
            editText_WriteComment.getText().setSpan(colorSpan, 0, editText_WriteComment.getText().length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        editText_WriteComment.requestFocus();
        editText_WriteComment.postDelayed(() -> {
            InputMethodManager manager = getContext().getSystemService(InputMethodManager.class);
            manager.showSoftInput(editText_WriteComment, InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }, 500);
        CommentFirestoreHelper.getInstance().setOnAddNewCommentListener(new CommentFirestoreHelper.FireStoreEventListener() {
            @Override
            public void onStart() {
                //TODO: Show the loading screen
            }

            @Override
            public void onComplete(boolean isSuccessful, Exception exception) {
                if (!isSuccessful || exception != null) {
                    Log.e("Error", "Error when try to add new comment", exception);
                    Toast.makeText(getContext(), R.string.unexpected_error_msg, Toast.LENGTH_SHORT).show();
                } else {
                    isCommit = true;
                    dismiss();
                }
                //TODO: Stop the loading screen
            }
        });
        startLoading();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        if (!isCommit)
            model.getCommentText().setValue(editText_WriteComment.getText().toString());
        else model.getCommentText().setValue("");
        super.onDismiss(dialog);
    }

    @Override
    public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
        if (startId == R.id.startLoading)
            buttonCommit.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.image_loader));
    }

    @Override
    public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
        //ignored
    }

    @Override
    public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
        if (currentId == R.id.endLoading)
            // Restart loading
            startLoading();
    }

    @Override
    public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {
        // ignored
    }

    private void startLoading() {

        mLayout.setTransition(R.id.transition_startLoading);
        mLayout.setProgress(0, 1);
    }
}