package com.example.splus;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.my_data.Course;
import com.example.splus.my_fragment.CommentFragment;

public class CourseActivity extends AppCompatActivity {
    TextView className;
    ImageButton buttonBack;
    Button buttonDetailClass;
    Button buttonContactTeacher;

    Course currentCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        currentCourse = (Course)bundle.get("course");

        className = findViewById(R.id.textNameCourseActivity);
        className.setText(currentCourse.getCourseName());

        RecyclerView recyclerList = findViewById(R.id.listCourseActivity);

        recyclerList.setLayoutManager(new LinearLayoutManager(this));

        Button buttonShowComment = findViewById(R.id.button_ShowComment);
        buttonShowComment.setOnClickListener(this::ShowComment);

        buttonBack = findViewById(R.id.buttonBackCourseActivity);
        buttonBack.setOnClickListener(v -> onBackPressed());

        buttonDetailClass = findViewById(R.id.buttonInfoCourseActivity);
        buttonDetailClass.setOnClickListener(view -> {
            Intent intent = new Intent(CourseActivity.this, DetailCourseActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("detail_course", currentCourse);
            intent.putExtras(b);
            startActivity(intent);
        });

        buttonContactTeacher = findViewById(R.id.buttonContactCourseActivity);
        buttonContactTeacher.setOnClickListener(view
                -> Toast.makeText(CourseActivity.this, "Contact button is pressed", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void ShowComment(View view) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_window_comment_reply, null, false);
        View holderView = findViewById(R.id.commentViewHolder);
        Transition slideExit = new Slide(Gravity.BOTTOM), slideBegin = new Slide(Gravity.BOTTOM);
        Display deviceDisplay = getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        deviceDisplay.getSize(displaySize);
        PopupWindow popupWindow = new PopupWindow(popupView, displaySize.x, (int)(displaySize.y * 0.85));
        slideExit.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {}

            @Override
            public void onTransitionEnd(Transition transition) {
                holderView.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionCancel(Transition transition) {}

            @Override
            public void onTransitionPause(Transition transition) {}

            @Override
            public void onTransitionResume(Transition transition) {}
        });
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setEnterTransition(slideBegin);
        popupWindow.setExitTransition(slideExit);
        popupWindow.setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.popup_background));
        holderView.setVisibility(View.VISIBLE);
        popupWindow.showAtLocation(holderView, Gravity.BOTTOM, 0, 0);
    }
}