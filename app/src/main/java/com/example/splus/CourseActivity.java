package com.example.splus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.my_data.Course;
import com.example.splus.my_fragment.CommentFragment;
import com.example.splus.my_viewmodel.CourseViewModel;

public class CourseActivity extends AppCompatActivity implements MotionLayout.TransitionListener {
    MotionLayout layout;
    TextView className;
    ImageButton buttonBack;
    ImageButton buttonDetailClass;
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

        buttonDetailClass = findViewById(R.id.imageButton_ShowCourseDetail);
        layout = findViewById(R.id.motionLayout_Course);
        buttonBack = findViewById(R.id.buttonBackCourseActivity);
        className = findViewById(R.id.textNameCourseActivity);
        RecyclerView recyclerList = findViewById(R.id.listCourseActivity);
        TextView textViewClassName = findViewById(R.id.textView_CourseName);
        TextView textViewInstructorName = findViewById(R.id.textView_InstructorName);
        TextView textViewCourseDetailShort = findViewById(R.id.textView_CourseDetail_Short);
        Button buttonShowComment = findViewById(R.id.button_ShowComment);

        layout.addTransitionListener(this);

        className.setText(currentCourse.getCourseName());

        textViewClassName.setText(currentCourse.getCourseName());

        textViewInstructorName.setText(currentCourse.getCreatorName());

        if (currentCourse.getCourseDescription() == null || currentCourse.getCourseDescription().isEmpty())
            textViewCourseDetailShort.setText(R.string.no_description);
        else textViewCourseDetailShort.setText(currentCourse.getCourseDescription());

        recyclerList.setLayoutManager(new LinearLayoutManager(this));

        buttonShowComment.setOnClickListener(this::ShowComment);

        buttonBack.setOnClickListener(v -> onBackPressed());

        buttonDetailClass.setOnClickListener(view -> {
            Intent intent = new Intent(CourseActivity.this, DetailCourseActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("courseDetail", currentCourse.getCourseDescription());
            intent.putExtras(b);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        if (isShowComment) {
            layout.setTransition(R.id.transitionCloseComment);
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

    // TransitionListener
    @Override
    public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {
    }
    @Override
    public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {
    }
    @Override
    public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
        if (currentId == R.id.transitionCloseComment)
            getSupportFragmentManager().popBackStack();
    }
    @Override
    public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {
    }
}