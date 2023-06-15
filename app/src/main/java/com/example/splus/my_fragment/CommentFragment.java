package com.example.splus.my_fragment;

import static android.app.Activity.RESULT_CANCELED;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import java.util.ArrayList;
import java.util.List;

public class CommentFragment extends Fragment {

    @Nullable
    public static CommentFragment INSTANCE;
    private final List<Comment> commentList = new ArrayList<>();
    private CourseViewModel model;
    private CommentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_comments, container, false);
        INSTANCE = this;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        model = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
        TextView textView_WriteComment = view.findViewById(R.id.textView_WriteComment);
        RecyclerView commentRecyclerView = view.findViewById(R.id.recyclerView_commentList);
        ActivityResultLauncher<Intent> launcher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    String commentString = result.getData().getStringExtra("commentString");
                    if (result.getResultCode() == RESULT_CANCELED)
                        textView_WriteComment.setText(commentString);
                    // else commit data as new comment
                });
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CommentAdapter(getContext(), commentList);
        adapter.setOnRepliesListener(new CommentAdapter.OnRepliesListener() {
            @Override
            public void onShowReply(Comment comment) {
                model.setCommentId(comment.getId());
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.add(R.id.fragmentContainerView_ShowComment, ReplyFragment.class, null);
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                transaction.commit();
            }

            @Override
            public void onCreateReply(Comment comment) {
                model.setCommentId(comment.getId());
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                Bundle args = new Bundle();
                args.putBoolean("createReply", true);
                transaction.add(R.id.fragmentContainerView_ShowComment, ReplyFragment.class, args);
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                transaction.commit();
            }
        });
        commentRecyclerView.setAdapter(adapter);

        textView_WriteComment.setOnClickListener(tv -> {
            //Intent intent = new Intent(getContext(), WriteCommentDialog.class);
            //intent.putExtra("commentString", textView_WriteComment.getText());
            //launcher.launch(intent);
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            Fragment fragment = getParentFragmentManager().findFragmentByTag("dialog");
            if (fragment != null)
                transaction.remove(fragment);
            WriteCommentDialog dialog = WriteCommentDialog.newInstance(textView_WriteComment.getText().toString());
            dialog.show(transaction, "dialog");
        });
        CommentFirestoreHelper helper = CommentFirestoreHelper.getInstance();
        helper.setOnGetCommentListListener(new CommentFirestoreHelper.OnGetDataListener() {
            @Override
            public void onStart() {
                // TODO: Show loading screen
            }

            @Override
            public void onComplete(boolean isSuccessful, Exception exception) {
                // TODO: Stop the loading screen
                adapter.notifyItemRangeInserted(0, commentList.size());
            }
        });
        helper.getListComment(model.getCurrentCourse().getCourseId(), commentList);
    }
}