package com.example.splus.my_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.R;
import com.example.splus.my_data.Course;

import java.text.DateFormat;
import java.util.Locale;

public class CourseAdapter extends ListAdapter<Course, CourseAdapter.CourseViewHolder> {

    private final OnItemClickListener listener;

    private final Context context;

    private final int role;

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

    public CourseAdapter(Context context, OnItemClickListener listener, int role) {
        super(DIFF_CALLBACK);
        this.listener = listener;
        this.context = context;
        this.role = role;
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
        holder.itemView.setOnClickListener(view -> listener.onItemClick(course));
        if (role == 0) {
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
        }
        holder.editButton.setOnClickListener(view -> listener.onEditButtonClick(course));
        holder.deleteButton.setOnClickListener(view -> listener.onDeleteButtonClick(course));
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseNameTextView;
        private final TextView creatorTextView;
        private final TextView creationTimeTextView;
        private final TextView studentCountTextView;

        private final ImageButton editButton, deleteButton;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            courseNameTextView = itemView.findViewById(R.id.courseNameTextView);
            creatorTextView = itemView.findViewById(R.id.creatorTextView);
            creationTimeTextView = itemView.findViewById(R.id.creationTimeTextView);
            studentCountTextView = itemView.findViewById(R.id.studentCountTextView);
            editButton = itemView.findViewById(R.id.imageButton_edit);
            deleteButton = itemView.findViewById(R.id.imageButton_delete);
        }
    }
  /*public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView textViewCourseName;
    private TextView textViewCreatorName;
    private Button buttonEdit;
    private Button buttonDelete;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      textViewCourseName = itemView.findViewById(R.id.courseNameTextView);
      textViewCreatorName = itemView.findViewById(R.id.creatorTextView);
      buttonEdit = itemView.findViewById(R.id.editButton);
      buttonDelete = itemView.findViewById(R.id.deleteButton);
    }

    public void bind(Course course, OnItemClickListener listener) {
      textViewCourseName.setText(course.getCourseName());
      textViewCreatorName.setText(course.getCreatorName());

      buttonEdit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          listener.onEditButtonClick(course);
        }
      });

      buttonDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          listener.onDeleteButtonClick(course);
        }
      });
    }
  }*/

  public interface OnItemClickListener {
    void onItemClick(Course course);

    void onEditButtonClick(Course course);

    void onDeleteButtonClick(Course course);
  }
}
