package com.example.splus.my_dialog;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.splus.R;
import com.example.splus.my_class.ReplyToOwnerSpan;
import com.example.splus.my_data.Comment;
import com.example.splus.my_firestore_helper.CommentFirestoreHelper;
import com.example.splus.my_firestore_helper.FirebaseStorageHelper;
import com.example.splus.my_viewmodel.CourseViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class WriteCommentDialog extends DialogFragment implements MotionLayout.TransitionListener {

    private EditText editText_WriteComment;

    private ReplyToOwnerSpan span;

    private ImageButton buttonCommit;

    private boolean isCommit;

    private String replyToOwnerName;
    private String replyToOwnerId;

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
        mLayout.setTransitionListener(this);

        model = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
        replyToOwnerName = model.getReplyToOwnerName().getValue();
        replyToOwnerId = model.getReplyToOwnerId().getValue();
        String parentCommentID = model.getParentCommentId();
        String courseID = model.getCurrentCourse().getCourseId();
        String editCommentID = model.getEditCommentId();
        String text = model.getCommentText().getValue();

        ImageView imageView_avatar = view.findViewById(R.id.imageView_Avatar_writeComment_dlg);
        editText_WriteComment = view.findViewById(R.id.editText_WriteComment);
        buttonCommit = view.findViewById(R.id.button_CommitComment);

        FirebaseStorageHelper.updateAvatar(FirebaseAuth.getInstance().getCurrentUser().getUid(), imageView_avatar);

        buttonCommit.setOnClickListener(v -> {
            if (editCommentID == null)
                // Add new comment
                CommentFirestoreHelper.getInstance().addNewComment(
                        courseID,
                        parentCommentID,
                        replyToOwnerId,
                        replyToOwnerName,
                        editText_WriteComment.getText().toString());
                // Edit the current comment
            else CommentFirestoreHelper.getInstance().edit(
                    editCommentID,
                    replyToOwnerId,
                    replyToOwnerName,
                    editText_WriteComment.getText().toString());
        });
        if (replyToOwnerName != null) {
            editText_WriteComment.setText(getString(R.string.tagOwnerName, replyToOwnerName, text));
            span = new ReplyToOwnerSpan(replyToOwnerId);
            editText_WriteComment.getText().setSpan(span, 0, replyToOwnerName.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else editText_WriteComment.setText(text);
        editText_WriteComment.requestFocus();
        editText_WriteComment.postDelayed(() -> {
            InputMethodManager manager = getContext().getSystemService(InputMethodManager.class);
            manager.showSoftInput(editText_WriteComment, InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }, 800);

        editText_WriteComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // ignored
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // ignored
            }

            @Override
            public void afterTextChanged(Editable editable) {
                buttonCommit.setEnabled(!editText_WriteComment.getText().toString().isBlank());
                if (replyToOwnerName == null) return;
                int pos = editable.getSpanEnd(span);
                if (pos != replyToOwnerName.length() + 1) {
                    editable.removeSpan(span);
                    replyToOwnerId = null;
                    replyToOwnerName = null;
                }
            }
        });
        CommentFirestoreHelper.getInstance().setOnAddNewCommentListener(new CommentFirestoreHelper.AddNewCommentListener() {

            @Override
            public void onStart() {
                startLoading();
            }

            @Override
            public void onSuccess(int replyCount) {
                model.getReplyCount().setValue(replyCount);
            }

            @Override
            public void onComplete(boolean isSuccessful, Exception exception) {
                stopLoading();
                if (!isSuccessful || exception != null) {
                    Log.e("Error", "Error when try to add new comment", exception);
                    Toast.makeText(getContext(), R.string.unexpected_error_msg, Toast.LENGTH_SHORT).show();
                } else {
                    isCommit = true;
                    dismiss();
                }

            }
        });
        CommentFirestoreHelper.getInstance().setOnEditListener(new CommentFirestoreHelper.FireStoreEventListener() {
            @Override
            public void onComplete(boolean isSuccessful, Exception exception) {
                stopLoading();
                if (!isSuccessful || exception != null) {
                    Log.e("Error", "Error when try to edit comment", exception);
                    Toast.makeText(getContext(), R.string.unexpected_error_msg, Toast.LENGTH_SHORT).show();
                } else {
                    isCommit = true;
                    dismiss();
                }
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        if (!isCommit)
            model.getCommentText().setValue(editText_WriteComment.getText().toString());
        else model.getCommentText().setValue("");
        model.getCommitSuccess().setValue(isCommit);
        super.onDismiss(dialog);
    }

    @Override
    public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
        if (startId == R.id.startLoading && endId == R.id.endLoading)
            buttonCommit.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.image_loader));
    }

    @Override
    public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
        //ignored
    }

    @Override
    public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
        if (currentId == R.id.endLoading)
            mLayout.setProgress(0, 1);
    }

    @Override
    public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {
        // ignored
    }

    private void startLoading() {
        buttonCommit.setEnabled(false);
        mLayout.setTransition(R.id.transition_commit_comment);
        mLayout.transitionToEnd();
    }

    private void stopLoading() {
        buttonCommit.setEnabled(true);
        mLayout.setTransition(R.id.startLoading, R.id.startLoading);
    }
}