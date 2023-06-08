package com.example.splus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class CreateClassActivity extends AppCompatActivity {

  private EditText editTextClassId;
  private EditText editTextClassName;
  private EditText editTextInstructorName;

  private FirebaseFirestore firestore;
  private FirebaseAuth firebaseAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_class);

    // Get references to Firestore and FirebaseAuth
    firestore = FirebaseFirestore.getInstance();
    firebaseAuth = FirebaseAuth.getInstance();

    editTextClassId = findViewById(R.id.editClassId);
    editTextClassName = findViewById(R.id.editClassName);
    editTextInstructorName = findViewById(R.id.editInstructorName);
    Button btnCreateClass = findViewById(R.id.btnCreateClass);

    btnCreateClass.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Get information from EditText fields
        String classId = editTextClassId.getText().toString().trim();
        String className = editTextClassName.getText().toString().trim();
        String instructorName = editTextInstructorName.getText().toString().trim();
        // Get the username of the current user from FirebaseAuth
        String username = firebaseAuth.getCurrentUser().getDisplayName();

        // Check if all fields have been entered
        if (classId.isEmpty() || className.isEmpty() || instructorName.isEmpty()) {
          Toast.makeText(CreateClassActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
          // Create a data object for the class
          Map<String, Object> classData = new HashMap<>();
          classData.put("classId", classId);
          classData.put("className", className);
          classData.put("instructorName", instructorName);
          classData.put("username", username);

          // Add the data object to the "classes" collection on Firestore
          CollectionReference classesCollection = firestore.collection("classes");
          classesCollection.document(classId).set(classData)
              .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                  Toast.makeText(CreateClassActivity.this, "Tạo lớp học thành công", Toast.LENGTH_SHORT).show();

                  // Clear input fields after successfully creating the class
                  editTextClassId.setText("");
                  editTextClassName.setText("");
                  editTextInstructorName.setText("");
                }
              })
              .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                  Toast.makeText(CreateClassActivity.this, "Đã xảy ra lỗi. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                }
              });
        }
      }
    });
  }
}
