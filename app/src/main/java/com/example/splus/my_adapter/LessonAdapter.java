package com.example.splus.my_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.R;
import com.example.splus.my_data.LessonData;
import com.example.splus.my_interface.IClickLessonListener;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.UserViewHolder> {

    private final List<LessonData> mListLesson;
    private final IClickLessonListener iClickLessonListener;

    public LessonAdapter(List<LessonData> mListLesson, IClickLessonListener iClickLessonListener) {
        this.mListLesson = mListLesson;
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
        LessonData lesson = mListLesson.get(position);
        if (lesson == null) {
            return;
        }
        holder.lessonImage.setImageResource(lesson.getLessonImage());
        holder.lessonName.setText(lesson.getLessonName());
        holder.className.setText(lesson.getClassName());
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
        if (mListLesson != null) {
            return mListLesson.size();
        }
        return 0;
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout lessonItem;
        private final ImageView lessonImage;
        private final TextView lessonName;
        private final TextView className;
        private final TextView teacherName;

        public UserViewHolder(@NonNull View lessonView) {
            super(lessonView);
            lessonItem = lessonView.findViewById(R.id.itemLessonLayout);
            lessonImage = lessonView.findViewById(R.id.lessonImage);
            lessonName = lessonView.findViewById(R.id.lessonName);
            className = lessonView.findViewById(R.id.className);
            teacherName = lessonView.findViewById(R.id.teacherName);
        }
    }
}
