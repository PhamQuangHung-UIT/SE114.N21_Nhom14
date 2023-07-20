package com.example.splus.my_fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.splus.my_adapter.my_item_animator.CustomItemAnimator;
import com.example.splus.my_dialog.LoadingDialog;
import com.example.splus.my_dialog.WriteCommentDialog;
import com.example.splus.my_adapter.CommentAdapter;
import com.example.splus.my_data.Comment;
import com.example.splus.my_firestore_helper.CommentFirestoreHelper;
import com.example.splus.my_firestore_helper.FirebaseStorageHelper;
import com.example.splus.my_viewmodel.CourseViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ReplyFragment extends Fragment implements CommentAdapter.CommentOptionListener {

    private List<Comment> replyList;

    private CourseViewModel model;
    private Comment comment;

    private LoadingDialog dialog;

    CommentFirestoreHelper helper = CommentFirestoreHelper.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_replies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        View commentView = view.findViewById(R.id.comment_view);
        model = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
        model.getFragmentType().setValue(CourseViewModel.REPLY);
        AtomicBoolean createReply = new AtomicBoolean(getArguments().getBoolean("createReply"));

        ImageView imageView_avatar = view.findViewById(R.id.imageView_AvatarWriteComment);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_ReplyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CommentAdapter adapter = new CommentAdapter(getContext());
        adapter.setCommentOptionListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new CustomItemAnimator());

        FirebaseStorageHelper.updateAvatar(FirebaseAuth.getInstance().getCurrentUser().getUid(), imageView_avatar);

        model.getCommitSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success && model.getFragmentType().getValue() == CourseViewModel.REPLY) {
                createReply.set(false);
                replyList = helper.getListReply(model.getCurrentCourse().getCourseId(), model.getParentCommentId());
            }
        });
        helper.setOnGetCommentDataListener(new CommentFirestoreHelper.GetCommentDataListener() {
            @Override
            public void onStart() {
                startLoading();
            }

            @Override
            public void onSuccess(Comment comment) {
                ReplyFragment.this.comment = comment;
                replyList = helper.getListReply(model.getCurrentCourse().getCourseId(), model.getParentCommentId());
            }

            @Override
            public void onComplete(boolean isSuccessful, Exception exception) {
                // Has error
                if (exception != null || !isSuccessful) {
                    Toast.makeText(getContext(), R.string.unexpected_error_msg, Toast.LENGTH_SHORT).show();
                    stopLoading();
                } else {
                    CommentAdapter.CommentViewHolder holder = new CommentAdapter.CommentViewHolder(commentView);
                    adapter.applyViewHolder(holder, comment, CommentAdapter.REPLY);
                }
            }
        });
        helper.setOnGetReplyListListener(new CommentFirestoreHelper.FireStoreEventListener() {
            @Override
            public void onStart() {
                // ignored
            }

            @Override
            public void onComplete(boolean isSuccessful, Exception exception) {
                if (!isSuccessful) {
                    Log.e("Error", "error in get comment list", exception);
                    Toast.makeText(getContext(), R.string.unexpected_error_msg, Toast.LENGTH_SHORT).show();
                } else {
                    adapter.submitList(replyList);
                    if (createReply.get())
                        onCreateReply(comment);
                }
                stopLoading();
            }
        });

        helper.getComment(model.getParentCommentId());
    }

    @Override
    public void onDestroyView() {
        model.getCommentText().setValue("");
        super.onDestroyView();
    }

    @Override
    public void onCreateReply(Comment comment) {
        model.setEditCommentId(null);
        model.getReplyToOwnerName().setValue(comment.getOwnerDisplayName());
        model.getReplyToOwnerId().setValue(comment.getOwnerId());
        model.getFragmentType().setValue(CourseViewModel.REPLY);
        writeNewComment("");
    }

    @Override
    public void onEdit(Comment comment) {
        model.setEditCommentId(comment.getId());
        model.getFragmentType().setValue(CourseViewModel.REPLY);
        writeNewComment(comment.getText());
    }

    @Override
    public void onDelete(Comment comment) {
        model.getFragmentType().setValue(CourseViewModel.REPLY);
        AlertDialog.Builder builder = new AlertDialog
                .Builder(getContext())
                .setMessage(R.string.delete_msg)
                .setNegativeButton(R.string.cancel, (dialog, flag) -> dialog.dismiss())
                .setPositiveButton(R.string.delete, (dialog, flag) -> CommentFirestoreHelper.getInstance().delete(comment));
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

    private void writeNewComment(String text) {
        model.getCommentText().setValue(text);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        Fragment fragment = getParentFragmentManager().findFragmentByTag("dialog");
        if (fragment != null)
            transaction.remove(fragment);
        WriteCommentDialog dialog = WriteCommentDialog.newInstance();
        dialog.show(transaction, "dialog");
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
        dialog.dismiss();
    }
}
