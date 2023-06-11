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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.R;
import com.example.splus.WriteCommentActivity;
import com.example.splus.my_adapter.CommentAdapter;
import com.example.splus.my_data.Comment;
import com.example.splus.my_data.Course;
import com.example.splus.my_viewmodel.CourseViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CommentFragment extends Fragment {

    @Nullable
    public static CommentFragment INSTANCE;
    private final List<Comment> commentList = new ArrayList<>();

    private CourseViewModel model;

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

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
        RecyclerView commentRecyclerView = view.findViewById(R.id.recyclerView_CommentList);
        ActivityResultLauncher<Intent> launcher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                    String commentString = result.getData().getStringExtra("commentString");
                    if (result.getResultCode() == RESULT_CANCELED)
                        textView_WriteComment.setText(commentString);
                });
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CommentAdapter adapter = new CommentAdapter(getContext(), commentList);
        commentRecyclerView.setAdapter(adapter);

        textView_WriteComment.setOnClickListener(tv -> {
            Intent intent = new Intent(getContext(), WriteCommentActivity.class);
            intent.putExtra("commentString", textView_WriteComment.getText());
            launcher.launch(intent);
        });
        // TODO: Retrieve comments
        //getCommentList();
    }

    private void getCommentList() {
        // Get course's comments
        Course course = model.getCurrentCourse();
        Query query = firestore.collection("comment").whereEqualTo("courseID", course.getCourseID());
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful())
                for (QueryDocumentSnapshot document: task.getResult()) {
                    //TODO: Get data
                }
        });
    }
}
