package com.example.splus.my_fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;

import com.example.splus.R;
import com.example.splus.my_adapter.my_item_animator.CustomItemAnimator;
import com.example.splus.my_dialog.LoadingDialog;
import com.example.splus.my_dialog.WriteCommentDialog;
import com.example.splus.my_adapter.CommentAdapter;
import com.example.splus.my_data.Comment;
import com.example.splus.my_firestore_helper.CommentFirestoreHelper;
import com.example.splus.my_firestore_helper.FirebaseStorageHelper;
import com.example.splus.my_viewmodel.CourseViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class CommentFragment extends Fragment implements CommentAdapter.CommentOptionListener {

    private List<Comment> commentList;
    private CourseViewModel model;
    private CommentAdapter adapter;

    private LoadingDialog dialog;

    private int index;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_comments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        model = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
        model.getFragmentType().setValue(CourseViewModel.COMMENT);
        // Avoid self-trigger observer when re-add the observer
        model.getCommitSuccess().setValue(false);

        TextView textView_WriteComment = view.findViewById(R.id.textView_WriteComment);
        RecyclerView commentRecyclerView = view.findViewById(R.id.recyclerView_commentList);
        ImageView imageView_avatar = view.findViewById(R.id.imageView_AvatarWriteComment);

        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CommentAdapter(getContext());
        adapter.setCommentOptionListener(this);
        commentRecyclerView.setAdapter(adapter);
        commentRecyclerView.setItemAnimator(new CustomItemAnimator());

        FirebaseStorageHelper.updateAvatar(FirebaseAuth.getInstance().getCurrentUser().getUid(), imageView_avatar);

        model.getCommentText().observe(getViewLifecycleOwner(), textView_WriteComment::setText);

        textView_WriteComment.setOnClickListener(tv -> {
            model.setParentCommentId(null);
            model.getReplyToOwnerId().setValue(null);
            model.getReplyToOwnerName().setValue(null);
            model.setEditCommentId(null);
            model.getCommentText().setValue(textView_WriteComment.getText().toString());
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            Fragment fragment = getParentFragmentManager().findFragmentByTag("dialog");
            if (fragment != null)
                transaction.remove(fragment);
            WriteCommentDialog dialog = WriteCommentDialog.newInstance();
            dialog.show(transaction, "dialog");
        });
        CommentFirestoreHelper helper = CommentFirestoreHelper.getInstance();
        helper.setOnGetCommentListListener(new CommentFirestoreHelper.FireStoreEventListener() {
            @Override
            public void onStart() {
                startLoading();
            }

            @Override
            public void onComplete(boolean isSuccessful, Exception exception) {
                stopLoading();
                if (!isSuccessful)
                {
                    Log.e("Error", "error in get comment list", exception);
                    Snackbar.make(getView(), R.string.unexpected_error_msg, Snackbar.LENGTH_SHORT).show();
                } else adapter.submitList(commentList);
            }
        });
        helper.setOnLikeChangeListener(new CommentFirestoreHelper.FireStoreEventListener() {
            @Override
            public void onComplete(boolean isSuccessful, Exception exception) {
                if (!isSuccessful || exception != null) {
                    Log.e("Error", "error in like a comment", exception);
                    Snackbar.make(getView(), R.string.unexpected_error_msg, Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        helper.setOnDislikeChangeListener(new CommentFirestoreHelper.FireStoreEventListener() {
            @Override
            public void onComplete(boolean isSuccessful, Exception exception) {
                if (!isSuccessful || exception != null) {
                    Log.e("Error", "error in like a comment", exception);
                    Snackbar.make(getView(), R.string.unexpected_error_msg, Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        helper.setOnDeleteListener(new CommentFirestoreHelper.FireStoreEventListener() {
            @Override
            public void onComplete(boolean isSuccessful, Exception exception) {
                if (!isSuccessful || exception != null) {
                    Log.e("Error", "error when try to delete a comment", exception);
                    Snackbar.make(getView(), R.string.unexpected_error_msg, Toast.LENGTH_SHORT).show();
                }
                model.getCommitSuccess().setValue(isSuccessful);
            }
        });

        model.getCommitSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success && model.getFragmentType().getValue() == CourseViewModel.COMMENT) {
                commentList = helper.getListComment(model.getCurrentCourse().getCourseId());
                // Auto trigger onComplete when get comment list
            }
        });

        model.getReplyCount().observe(getViewLifecycleOwner(), replyCount ->
            commentList.get(index).setReplyCount(replyCount));

        commentList = helper.getListComment(model.getCurrentCourse().getCourseId());
    }

    @Override
    public void onDestroyView() {
        model.getReplyCount().removeObservers(getViewLifecycleOwner());
        model.getCommitSuccess().removeObservers(getViewLifecycleOwner());
        super.onDestroyView();
    }

    @Override
    public void onShowReply(Comment comment) {
        model.setParentCommentId(comment.getId());
        ReplyFragment fragment = new ReplyFragment();
        fragment.setArguments(new Bundle());
        fragment.setEnterTransition(new Slide(Gravity.RIGHT));
        fragment.setExitTransition(new Slide(Gravity.RIGHT));
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainerView_ShowComment, fragment).commit();
    }

    @Override
    public void onCreateReply(Comment comment) {
        index = commentList.indexOf(comment);
        model.setParentCommentId(comment.getId());
        model.getFragmentType().setValue(CourseViewModel.COMMENT);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        ReplyFragment fragment = new ReplyFragment();
        Bundle arg = new Bundle();
        arg.putBoolean("createReply", true);
        fragment.setArguments(arg);
        fragment.setEnterTransition(new Slide(Gravity.RIGHT));
        fragment.setExitTransition(new Slide(Gravity.RIGHT));
        transaction.add(R.id.fragmentContainerView_ShowComment, fragment).commit();
    }

    @Override
    public void onEdit(Comment comment) {
        model.getCommentText().setValue(comment.getText());
        model.setEditCommentId(comment.getId());
        model.getFragmentType().setValue(CourseViewModel.COMMENT);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        Fragment fragment = getParentFragmentManager().findFragmentByTag("dialog");
        if (fragment != null)
            transaction.remove(fragment);
        WriteCommentDialog dialog = WriteCommentDialog.newInstance();
        dialog.show(transaction, "dialog");
    }

    @Override
    public void onDelete(Comment comment) {
        model.getFragmentType().setValue(CourseViewModel.COMMENT);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setMessage(R.string.delete_msg)
                .setNegativeButton(R.string.cancel, (dialog, flag) -> dialog.dismiss())
                .setPositiveButton(R.string.delete, (dialog, flag) -> CommentFirestoreHelper.getInstance().delete(comment));
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setAllCaps(false);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setAllCaps(false);
    }

    @Override
    public void onLikeChange(Comment comment, int state, int previousState) {
        CommentFirestoreHelper.getInstance().likeChange(comment, state, previousState);
    }

    @Override
    public void onDislikeChange(Comment comment, int state, int previousState) {
        CommentFirestoreHelper.getInstance().dislikeChange(comment, state, previousState);
    }

    private void startLoading() {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        Fragment fragment = getParentFragmentManager().findFragmentByTag("loading");
        if (fragment != null)
            return;
        dialog = LoadingDialog.getInstance();
        dialog.show(transaction, "loading");
    }

    private void stopLoading() {
        if (dialog.isAdded())
            dialog.dismiss();
    }
}