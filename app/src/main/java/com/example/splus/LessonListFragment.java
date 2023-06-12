package com.example.splus;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.splus.my_adapter.LessonAdapter;
import com.example.splus.my_data.Course;
import com.example.splus.my_data.Lesson;
import com.example.splus.my_viewmodel.CourseViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class LessonListFragment extends Fragment {
    MainActivity activity = (MainActivity) getActivity();
    List<Lesson> listLesson = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lesson_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CourseViewModel viewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);

        Course myClass = viewModel.getCurrentCourse();

        RecyclerView recyclerList = view.findViewById(R.id.listCourseActivity);

        recyclerList.setLayoutManager(new LinearLayoutManager(getContext()));

        getLessonList(activity.listLessonId);
        LessonAdapter lessonAdapter = new LessonAdapter(this.listLesson, lesson -> {
            Intent intent = new Intent(getContext(), StudyActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("lesson", lesson);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        recyclerList.setAdapter(lessonAdapter);
    }

    private void getLessonList(List<String> listLessonId) {
        listLesson = new ArrayList<>();
        for (int index=0; index < listLessonId.size(); index++) {
            activity.db.collection("lessons").document(listLessonId.get(index))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document != null) {
                                    listLesson.add(new Lesson(
                                            document.getId(),
                                            document.getString("name"),
                                            document.getString("content"),
                                            document.getString("courseName"),
                                            document.getString("teacherName")
                                    ));
                                }
                            }
                        }
                    });
        }

    }

}