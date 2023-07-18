package com.example.splus.my_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.CourseActivity;
import com.example.splus.R;
import com.example.splus.my_adapter.CourseAdapter;
import com.example.splus.my_data.Course;
import com.example.splus.my_dialog.LoadingDialog;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class CoursesFragment extends Fragment implements CourseAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;

    private LoadingDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses, container, false);

        recyclerView = view.findViewById(R.id.recyclerListCoursesFragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        courseAdapter = new CourseAdapter(getContext(), course -> {
            Intent intent = new Intent(getContext(), CourseActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("course", course);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        recyclerView.setAdapter(courseAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //load courses list
        loadCourses();
    }

    private void loadCourses() {
        startLoading();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Query query = db.collection("enrollment").whereEqualTo("accountId", user.getUid());
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Course> list = new ArrayList<>();
                List<Task<DocumentSnapshot>> taskList = new ArrayList<>();
                task.getResult().forEach(queryDocumentSnapshot -> {
                    String id = queryDocumentSnapshot.get("courseId", String.class);
                    taskList.add(db.collection("course").document(id).get().addOnSuccessListener(documentSnapshot ->
                        list.add(documentSnapshot.toObject(Course.class))));
                });
                try {
                    Tasks.await(Tasks.whenAll(taskList));
                    courseAdapter.submitList(list);
                } catch (Exception e) {
                    Log.e("Error", "Error when get course list", e);
                }
            } else {
                Log.e("Error", "Error when get course list", task.getException());
                Snackbar.make(getView(), R.string.unexpected_error_msg, Snackbar.LENGTH_SHORT).show();
            }
            stopLoading();
        });
    }

    private void startLoading() {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        Fragment fragment = getParentFragmentManager().findFragmentByTag("loading");
        if (fragment != null)
            transaction.remove(fragment);
        dialog = LoadingDialog.getInstance();
        dialog.show(transaction, "loading");
    }

    private void stopLoading() {
        dialog.dismiss();
    }
}
