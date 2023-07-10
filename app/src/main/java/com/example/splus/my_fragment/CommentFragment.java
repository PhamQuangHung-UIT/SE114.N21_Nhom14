package com.example.splus.my_fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.splus.R;
import com.example.splus.WriteCommentDialog;
import com.example.splus.my_adapter.CommentAdapter;
import com.example.splus.my_data.Comment;
import com.example.splus.my_firestore_helper.CommentFirestoreHelper;
import com.example.splus.my_viewmodel.CourseViewModel;

import java.util.ArrayList;
import java.util.List;

public class CommentFragment extends Fragment implements CommentAdapter.CommentOptionListener {

    private List<Comment> commentList;
    private CourseViewModel model;
    private CommentAdapter adapter;

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

        TextView textView_WriteComment = view.findViewById(R.id.textView_WriteComment);
        RecyclerView commentRecyclerView = view.findViewById(R.id.recyclerView_commentList);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CommentAdapter(getContext());
        adapter.setCommentOptionListener(this);
        commentRecyclerView.setAdapter(adapter);

        model.getCommentText().observe(getViewLifecycleOwner(), textView_WriteComment::setText);

        textView_WriteComment.setOnClickListener(tv -> {
            model.setParentCommentId(null);
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
                // TODO: Show loading screen
            }

            @Override
            public void onComplete(boolean isSuccessful, Exception exception) {
                // TODO: Stop the loading screen
                if (!isSuccessful)
                {
                    Log.e("Error", "error in get comment list", exception);
                    Toast.makeText(getContext(), R.string.unexpected_error_msg, Toast.LENGTH_SHORT).show();
                } else adapter.submitList(commentList);
            }
        });
        commentList = helper.getListComment(model.getCurrentCourse().getCourseId(), null);
    }

    @Override
    public void onShowReply(Comment comment) {
        model.setParentCommentId(comment.getId());
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainerView_ShowComment, ReplyFragment.class, new Bundle());
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.commit();
    }

    @Override
    public void onCreateReply(Comment comment) {
        model.setParentCommentId(comment.getId());
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putBoolean("createReply", true);
        transaction.add(R.id.fragmentContainerView_ShowComment, ReplyFragment.class, args);
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.commit();
    }

    @Override
    public void onEdit(Comment comment) {
        model.getCommentText().setValue(comment.getText());
        model.setEditCommentId(comment.getId());
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        Fragment fragment = getParentFragmentManager().findFragmentByTag("dialog");
        if (fragment != null)
            transaction.remove(fragment);
        WriteCommentDialog dialog = WriteCommentDialog.newInstance();
        dialog.show(transaction, "dialog");
    }

    @Override
    public void onDelete(Comment comment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setMessage(R.string.delete_msg)
                .setCancelable(true)
                .setPositiveButton("OK", (dialog, flag) -> CommentFirestoreHelper.getInstance().delete(comment));
        builder.create().show();
    }

    @Override
    public void onLikeChange(Comment comment, int state, int previousState) {
        CommentFirestoreHelper.getInstance().likeChange(comment, state, previousState);
    }

    @Override
    public void onDislikeChange(Comment comment, int state, int previousState) {
        CommentFirestoreHelper.getInstance().dislikeChange(comment, state, previousState);
    }
}