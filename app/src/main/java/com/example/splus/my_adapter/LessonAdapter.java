package com.example.splus.my_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.R;
import com.example.splus.my_data.Lesson;
import com.example.splus.my_interface.IClickLessonListener;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.UserViewHolder> {

    private final List<Lesson> lessonList;
    private final IClickLessonListener iClickLessonListener;

    public LessonAdapter(List<Lesson> lessonList, IClickLessonListener iClickLessonListener) {
        this.lessonList = lessonList;
        this.iClickLessonListener = iClickLessonListener;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Lesson lesson = lessonList.get(position);
        if (lesson == null) {
            return;
        }
        holder.lessonName.setText(lesson.getLessonName());
        holder.courseName.setText(lesson.getCourseName());
        holder.teacherName.setText(lesson.getTeacherName());

        holder.lessonItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickLessonListener.onClickLesson(lesson);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (lessonList != null) {
            return lessonList.size();
        }
        return 0;
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout lessonItem;
        private final TextView lessonName;
        private final TextView courseName;
        private final TextView teacherName;

        public UserViewHolder(@NonNull View lessonView) {
            super(lessonView);
            lessonItem = lessonView.findViewById(R.id.relativeItemLesson);
            lessonName = lessonView.findViewById(R.id.textLessonNameItemLesson);
            courseName = lessonView.findViewById(R.id.textClassNameItemLesson);
            teacherName = lessonView.findViewById(R.id.textTeacherNameItemLesson);
        }
    }
}
