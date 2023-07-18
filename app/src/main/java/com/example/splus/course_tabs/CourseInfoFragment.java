package com.example.splus.course_tabs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.splus.R;
import com.example.splus.my_data.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class CourseInfoFragment extends Fragment {

  private StudentAdapter studentAdapter;
  private FirebaseFirestore firebaseFirestore;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_course_info, container, false);

    RecyclerView recyclerView = view.findViewById(R.id.recyclerViewInfo);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    studentAdapter = new StudentAdapter();
    recyclerView.setAdapter(studentAdapter);

    firebaseFirestore = FirebaseFirestore.getInstance();
    fetchStudentsFromFirebase();

    return view;
  }

  private void fetchStudentsFromFirebase() {
    firebaseFirestore.collection("students")
        .get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
          @Override
          public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
              List<Student> students = new ArrayList<>();
              for (DocumentSnapshot document : task.getResult()) {
                String name = document.getString("name");
                String email = document.getString("email");
                Student student = new Student(name, email);
                students.add(student);
              }
              studentAdapter.setStudentList(students);
            }  // Xử lý khi lấy dữ liệu không thành công

          }
        });
  }

  private static class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private List<Student> studentList;

    @SuppressLint("NotifyDataSetChanged")
    public void setStudentList(List<Student> studentList) {
      this.studentList = studentList;
      notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      Student student = studentList.get(position);
      holder.bind(student);
    }

    @Override
    public int getItemCount() {
      return studentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

      private final TextView textViewName;
      private final TextView textViewEmail;

      public ViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewName = itemView.findViewById(R.id.textViewStudentName);
        textViewEmail = itemView.findViewById(R.id.textViewStudentEmail);
      }

      public void bind(Student student) {
        textViewName.setText(student.getName());
        textViewEmail.setText(student.getEmail());
      }
    }
  }
}
