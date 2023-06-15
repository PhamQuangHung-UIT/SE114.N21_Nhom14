package com.example.splus.my_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.R;
import com.example.splus.my_adapter.CommentAdapter;
import com.example.splus.my_data.Comment;
import com.example.splus.my_firestore_helper.CommentFirestoreHelper;
import com.example.splus.my_viewmodel.CourseViewModel;

import java.util.List;

public class ReplyFragment extends Fragment {

    private List<Comment> replyList;

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
        CourseViewModel model = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_ReplyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CommentAdapter adapter = new CommentAdapter(getContext(), replyList);
        adapter.setOnRepliesListener(new CommentAdapter.OnRepliesListener() {
            @Override
            public void onCreateReply(Comment comment) {
                createReply();
            }
        });
        recyclerView.setAdapter(adapter);
        helper.setOnGetCommentDataListener(new CommentFirestoreHelper.OnGetDataListener() {
            @Override
            public void onStart() {
                // TODO: Start the loading screen
            }

            @Override
            public void onComplete(boolean isSuccessful, Exception exception) {
                if (exception != null || !isSuccessful) {
                    Toast.makeText(getContext(), R.string.unexpected_error_msg, Toast.LENGTH_SHORT).show();
                    // TODO: Stop the loading screen
                }
                CommentAdapter.CommentViewHolder holder = new CommentAdapter.CommentViewHolder(commentView);
                adapter.applyViewHolder(holder, comment, CommentAdapter.REPLY);
                helper.setOnGetReplyListListener(new CommentFirestoreHelper.OnGetDataListener() {
                    @Override
                    public void onStart() {
                        // ignored
                    }

                    @Override
                    public void onComplete(boolean isSuccessful, Exception exception) {
                        // TODO: Stop the loading screen
                        adapter.notifyItemRangeInserted(0, replyList.size());
                    }
                });
                helper.getListReplies(model.getCurrentCourse().getCourseId(), model.getCommentId(), replyList);
            }
        });
        helper.getComment(model.getCommentId(), comment);
    }

    private void createReply() {
    }
}
