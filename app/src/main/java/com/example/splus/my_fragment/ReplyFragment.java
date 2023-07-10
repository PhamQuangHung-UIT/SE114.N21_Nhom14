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

import java.util.List;

public class ReplyFragment extends Fragment implements CommentAdapter.CommentOptionListener {

    private List<Comment> replyList;

    private CourseViewModel model;
    private Comment comment;

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
        boolean createReply = getArguments().getBoolean("createReply");

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_ReplyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CommentAdapter adapter = new CommentAdapter(getContext());
        adapter.setCommentOptionListener(this);
        recyclerView.setAdapter(adapter);
        helper.setOnGetCommentDataListener(new CommentFirestoreHelper.FireStoreEventListener() {
            @Override
            public void onStart() {
                // TODO: Start the loading screen
            }

            @Override
            public void onComplete(boolean isSuccessful, Exception exception) {
                // Has error
                if (exception != null || !isSuccessful) {
                    Toast.makeText(getContext(), R.string.unreached_server_msg, Toast.LENGTH_SHORT).show();
                    // TODO: Stop the loading screen
                } else {
                    helper.setOnGetCommentListListener(new CommentFirestoreHelper.FireStoreEventListener() {
                        @Override
                        public void onStart() {
                            // ignored
                        }

                        @Override
                        public void onComplete(boolean isSuccessful, Exception exception) {
                            // TODO: Stop the loading screen
                            if (!isSuccessful) {
                                Log.e("Error", "error in get comment list", exception);
                                Toast.makeText(getContext(), R.string.unexpected_error_msg, Toast.LENGTH_SHORT).show();
                            } else {
                                CommentAdapter.CommentViewHolder holder = new CommentAdapter.CommentViewHolder(commentView);
                                adapter.applyViewHolder(holder, comment, CommentAdapter.REPLY);
                                adapter.submitList(replyList);
                                if (createReply)
                                   writeNewComment("");
                            }
                        }
                    });
                    replyList = helper.getListComment(model.getCurrentCourse().getCourseId(), model.getParentCommentId());
                }
            }
        });
        comment = helper.getComment(model.getParentCommentId());
    }

    @Override
    public void onCreateReply(Comment comment) {
        model.setEditCommentId(null);
        TextView textView_WriteReply = getView().findViewById(R.id.textView_WriteReply);
        writeNewComment(textView_WriteReply.getText().toString());
    }

    @Override
    public void onEdit(Comment comment) {
        model.setEditCommentId(comment.getId());
        writeNewComment(comment.getText());

    }

    @Override
    public void onDelete(Comment comment) {

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
}
