package com.example.splus.my_fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.CourseActivity;
import com.example.splus.CreateCourseActivity;
import com.example.splus.EditCourseActivity;
import com.example.splus.MainActivity;
import com.example.splus.R;
import com.example.splus.my_adapter.CourseAdapter;
import com.example.splus.my_data.Account;
import com.example.splus.my_data.Course;
import com.example.splus.my_dialog.LoadingDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class CoursesFragment extends Fragment implements CourseAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;

    private LoadingDialog dialog;

    ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK)
                    loadCourses();
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses, container, false);

        Button createBtn = view.findViewById(R.id.btn_create_course);
        Account account = MainActivity.getAccount();

        if (account.getRole() == 0)
            createBtn.setVisibility(View.GONE);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateCourseActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = view.findViewById(R.id.recyclerListCoursesFragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        courseAdapter = new CourseAdapter(getContext(), this, MainActivity.getAccount().getRole());
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
        List<Course> list = new ArrayList<>();
        if (MainActivity.getAccount().getRole() == 0) {
            Query query = db.collection("enrollment").whereEqualTo("accountId", user.getUid());
            query.get().continueWithTask(task -> {
                List<Task<DocumentSnapshot>> taskList = new ArrayList<>();
                task.getResult().forEach(queryDocumentSnapshot -> {
                    String id = queryDocumentSnapshot.get("courseId", String.class);
                    taskList.add(db.collection("courses").document(id).get().addOnSuccessListener(documentSnapshot ->
                            list.add(documentSnapshot.toObject(Course.class))));
                });
                return Tasks.whenAll(taskList);
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    courseAdapter.submitList(list);
                } else {
                    Log.e("Error", "Error when get course list", task.getException());
                    Snackbar.make(getView(), R.string.unexpected_error_msg, Snackbar.LENGTH_SHORT).show();
                }
                stopLoading();
            });
        } else {
            Query query = db.collection("courses").whereEqualTo("creatorId", user.getUid());
            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    list.addAll(task.getResult().toObjects(Course.class));
                    courseAdapter.submitList(list);
                } else {
                    Log.e("Error", "Error when get course list", task.getException());
                    Snackbar.make(getView(), R.string.unexpected_error_msg, Snackbar.LENGTH_SHORT).show();
                }
                stopLoading();
            });
        }
    }

    @Override
    public void onItemClick(Course course) {
        Intent intent = new Intent(getContext(), CourseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("course", course);
        intent.putExtras(bundle);
        launcher.launch(intent);
    }

    @Override
    public void onEditButtonClick(Course course) {
        Intent intent = new Intent(getContext(), EditCourseActivity.class);
        intent.putExtra("course", course);
        launcher.launch(intent);
    }

    @Override
    public void onDeleteButtonClick(Course course) {
        // Implement the delete course functionality
        FirebaseFirestore.getInstance().collection("courses")
                .document(course.getCourseId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Course deleted successfully", Toast.LENGTH_SHORT).show();
                        loadCourses();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed to delete course", Toast.LENGTH_SHORT).show();
                    }
                });
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
