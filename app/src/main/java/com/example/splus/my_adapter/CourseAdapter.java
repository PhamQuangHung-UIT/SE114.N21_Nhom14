package com.example.splus.my_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.R;
import com.example.splus.my_data.Course;
import com.example.splus.my_interface.IClickCourseListener;

import java.text.DateFormat;
import java.util.Locale;

public class CourseAdapter extends ListAdapter<Course, CourseAdapter.CourseViewHolder> {

    private final IClickCourseListener listener;

    private final Context context;

    public static final DiffUtil.ItemCallback<Course> DIFF_CALLBACK = new DiffUtil.ItemCallback<>() {
        @Override
        public boolean areItemsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Course oldItem, @NonNull Course newItem) {
            return oldItem.equals(newItem);
        }
    };

    public CourseAdapter(Context context, IClickCourseListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = getCurrentList().get(position);
        DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("vn"));
        holder.courseNameTextView.setText(course.getCourseName());
        holder.creatorTextView.setText(context.getString(R.string.title_teacher_placeholder, course.getCreatorName()));
        holder.creationTimeTextView.setText(context.getString(R.string.title_create_date_placeholder,  formatter.format(course.getCreationTime().toDate())));
        holder.studentCountTextView.setText(context.getString(R.string.title_student_count_placeholder, course.getStudentCount()));
        holder.itemView.setOnClickListener(view -> listener.onClickCourse(course));
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseNameTextView;
        private final TextView creatorTextView;
        private final TextView creationTimeTextView;
        private final TextView studentCountTextView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            courseNameTextView = itemView.findViewById(R.id.courseNameTextView);
            creatorTextView = itemView.findViewById(R.id.creatorTextView);
            creationTimeTextView = itemView.findViewById(R.id.creationTimeTextView);
            studentCountTextView = itemView.findViewById(R.id.studentCountTextView);
        }
    }
}
