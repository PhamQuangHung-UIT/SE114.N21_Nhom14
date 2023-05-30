package com.example.splus.my_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.NotificationActivity;
import com.example.splus.R;
import com.example.splus.StudyActivity;
import com.example.splus.my_adapter.LessonAdapter;
import com.example.splus.my_data.Lesson;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recentLesson = view.findViewById(R.id.recentLessonList);
        recentLesson.setLayoutManager(new LinearLayoutManager(getActivity()));

        LessonAdapter lessonAdapter = new LessonAdapter(getRecentLesson(), this::onClickGoToLesson);

        recentLesson.setAdapter(lessonAdapter);

        ImageButton imageButtonNotif = view.findViewById(R.id.imageNotifyHomeFragment);
        imageButtonNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToNotification();
            }
        });
        return view;
    }

    private void onClickGoToNotification() {
        Intent intent = new Intent(this.getActivity(), NotificationActivity.class);
        startActivity(intent);
    }

    @NonNull
    private List<Lesson> getRecentLesson() {
        List<Lesson> lessonList = new ArrayList<>();
        lessonList.add(new Lesson(
                0,
                "Lesson 1",
                "Content",
                "Course name",
                "Teacher name"
        ));
        lessonList.add(new Lesson(
                0,
                "Lesson 2",
                "Content",
                "Course name",
                "Teacher name"
        ));
        lessonList.add(new Lesson(
                0,
                "Lesson 3",
                "Content",
                "Course name",
                "Teacher name"
        ));
        lessonList.add(new Lesson(
                0,
                "Lesson 4",
                "Content",
                "Course name",
                "Teacher name"
        ));
        return lessonList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void onClickGoToLesson(Lesson lesson) {
        Intent intent = new Intent(this.getActivity(), StudyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("lesson", lesson);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
