package com.example.splus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.my_data.Course;
import com.example.splus.my_fragment.CommentFragment;
import com.example.splus.my_viewmodel.CourseViewModel;

public class CourseActivity extends AppCompatActivity {
    MotionLayout layout;
    TextView className;
    ImageButton buttonBack;
    Button buttonDetailClass;
    Button buttonContactTeacher;
    Course currentCourse;

    private boolean isShowComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        CourseViewModel model = new ViewModelProvider(this).get(CourseViewModel.class);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        currentCourse = (Course)bundle.get("course");

        model.setCurrentCourse(currentCourse);

        layout = findViewById(R.id.motionLayout_Course);
        layout.addTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
            }
            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {

            }
            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
                if (currentId == R.id.transitionSwipeDownFromStartToEnd)
                    getSupportFragmentManager().popBackStack();
            }
            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {

            }
        });

        className = findViewById(R.id.textNameCourseActivity);
        className.setText(currentCourse.getCourseName());

        RecyclerView recyclerList = findViewById(R.id.listCourseActivity);

        recyclerList.setLayoutManager(new LinearLayoutManager(this));

        Button buttonShowComment = findViewById(R.id.button_ShowComment);
        buttonShowComment.setOnClickListener(this::ShowComment);

        buttonBack = findViewById(R.id.buttonBackCourseActivity);
        buttonBack.setOnClickListener(v -> onBackPressed());

        TextView textView = findViewById(R.id.textView_CourseDetail_Short);
        buttonDetailClass.setOnClickListener(view -> {
            Intent intent = new Intent(CourseActivity.this, DetailCourseActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("detail_course", currentCourse);
            intent.putExtras(b);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        if (isShowComment) {
            layout.setTransition(R.id.transitionSwipeDownFromStartToEnd);
            layout.setProgress(0, 1);
            isShowComment = false;
        } else finish();
    }

    private void ShowComment(View view) {
        layout.setTransition(R.id.transitionShowComment);
        layout.setProgress(0, 1);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragmentContainerView_ShowComment, CommentFragment.class, null);
        transaction.commit();
        isShowComment = true;
    }
}