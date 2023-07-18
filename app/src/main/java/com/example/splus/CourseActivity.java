package com.example.splus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.my_data.Course;
import com.example.splus.my_dialog.LoadingDialog;
import com.example.splus.my_fragment.CommentFragment;
import com.example.splus.my_viewmodel.CourseViewModel;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseActivity extends AppCompatActivity implements MotionLayout.TransitionListener {
    MotionLayout layout;
    TextView className;
    ImageButton buttonBack;
    ImageButton buttonDetailClass;
    Course currentCourse;

    Button buttonShowComment, buttonEnroll;

    LoadingDialog dialog;

    DocumentReference enrollRef;

    private boolean isShowComment, hasEnroll;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        CourseViewModel model = new ViewModelProvider(this).get(CourseViewModel.class);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        currentCourse = bundle.getParcelable("course");

        model.setCurrentCourse(currentCourse);

        buttonDetailClass = findViewById(R.id.imageButton_ShowCourseDetail);
        layout = findViewById(R.id.motionLayout_Course);
        buttonBack = findViewById(R.id.buttonBackCourseActivity);
        className = findViewById(R.id.textNameCourseActivity);
        RecyclerView recyclerList = findViewById(R.id.listCourseActivity);
        TextView textViewClassName = findViewById(R.id.textView_CourseName);
        TextView textViewInstructorName = findViewById(R.id.textView_InstructorName);
        TextView textViewCourseDetailShort = findViewById(R.id.textView_CourseDetail_Short);
        buttonShowComment = findViewById(R.id.button_ShowComment);
        buttonEnroll = findViewById(R.id.button_enroll);
        TextView title = findViewById(R.id.textView_CommentSectionTitle);
        ImageButton backButton = findViewById(R.id.imageButton_CommentSectionBack);

        layout.addTransitionListener(this);

        checkUserEnrollCourse();

        className.setText(currentCourse.getCourseName());

        textViewClassName.setText(currentCourse.getCourseName());

        textViewInstructorName.setText(currentCourse.getCreatorName());

        if (currentCourse.getCourseDescription() == null || currentCourse.getCourseDescription().isEmpty())
            textViewCourseDetailShort.setText(R.string.no_description);
        else textViewCourseDetailShort.setText(currentCourse.getCourseDescription());

        recyclerList.setLayoutManager(new LinearLayoutManager(this));

        buttonShowComment.setOnClickListener(this::showComment);

        buttonBack.setOnClickListener(v -> onBackPressed());

        backButton.setOnClickListener(v -> onBackPressed());

        buttonDetailClass.setOnClickListener(view -> {
            Intent intent = new Intent(CourseActivity.this, DetailCourseActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("courseDetail", currentCourse.getCourseDescription());
            intent.putExtras(b);
            startActivity(intent);
        });

        buttonEnroll.setOnClickListener(view -> {
            if (hasEnroll) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setMessage(R.string.unenroll_msg)
                        .setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.dismiss())
                        .setPositiveButton(R.string.ok, (dialogInterface, i) -> updateEnroll());
                builder.create().show();
            } else updateEnroll();
        });

        model.getFragmentType().observe(this, type -> {
            if (type == CourseViewModel.COMMENT) {
                title.setText(R.string.comments);
                backButton.setVisibility(View.GONE);
            } else {
                title.setText(R.string.reply);
                backButton.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isShowComment) {
            if (getSupportFragmentManager().getFragments().size() > 1)
                removeFragments(1);
            else {
                layout.setTransition(R.id.transitionCloseComment);
                layout.setProgress(0, 1);
                isShowComment = false;
            }
        } else finish();
    }

    private void showComment(View view) {
        layout.setTransition(R.id.transitionShowComment);
        layout.setProgress(0, 1);
        buttonShowComment.setEnabled(false);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragmentContainerView_ShowComment, CommentFragment.class, null);
        transaction.commit();
        isShowComment = true;
    }

    // TransitionListener
    @Override
    public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
    }
    @Override
    public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
    }
    @Override
    public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
        if (currentId == R.id.end) {
            removeFragments(2);
            buttonShowComment.setEnabled(true);
        }
    }
    @Override
    public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {
    }

    private void removeFragments(int number) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (!fragments.isEmpty()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            for (int i = fragments.size() - 1; i >= fragments.size() - number && i >= 0; i--)
                transaction.remove(fragments.get(i));
            transaction.commit();
        }
    }

    private void updateEnroll() {
        Task<?> newTask;
        hasEnroll = !hasEnroll;
        if (hasEnroll) {
            Map<String, Object> map = new HashMap<>();
            map.put("courseId", currentCourse.getCourseId());
            map.put("accountId", user.getUid());
            newTask = db.collection("enrollment").add(map);

        } else newTask = enrollRef.delete();
        newTask.continueWithTask(task -> db.runTransaction(transaction -> {
            DocumentReference ref = db.collection("courses").document(currentCourse.getCourseId());
            int studentCount = transaction.get(ref).get("studentCount", Integer.class);
            transaction.update(ref, "studentCount", hasEnroll ? ++studentCount : --studentCount);
            return studentCount;
        })).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                currentCourse.setStudentCount(task.getResult());
                 Snackbar.make(findViewById(R.id.motionLayout_Course), R.string.enroll_success_msg, Snackbar.LENGTH_SHORT)
                        .setAction(R.string.undo, view -> buttonEnroll.performClick()).show();
            } else {
                // Rollback transaction
                hasEnroll = !hasEnroll;
                Toast.makeText(this, R.string.unexpected_error_msg, Toast.LENGTH_SHORT).show();
            }
            if (hasEnroll)
                buttonEnroll.setText(R.string.unenroll);
            else buttonEnroll.setText(R.string.enroll);
        });
    }
    private void checkUserEnrollCourse() {
        startLoading();
        db.collection("enrollment")
                .whereEqualTo("courseId", currentCourse.getCourseId())
                .whereEqualTo("accountId", user.getUid())
                .get().addOnCompleteListener(task -> {
                   if (task.isSuccessful()) {
                       hasEnroll = !task.getResult().isEmpty();
                       enrollRef = task.getResult().getDocuments().get(0).getReference();
                       if (hasEnroll)
                           buttonEnroll.setText(R.string.unenroll);
                   } else Snackbar.make(findViewById(R.id.motionLayout_Course), R.string.unexpected_error_msg, Toast.LENGTH_SHORT).show();
                   stopLoading();
                });
    }
    private void startLoading() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("loading");
        if (fragment != null)
            transaction.remove(fragment);
        dialog = LoadingDialog.getInstance();
        dialog.show(transaction, "loading");
    }

    private void stopLoading() {
        dialog.dismiss();
    }
}