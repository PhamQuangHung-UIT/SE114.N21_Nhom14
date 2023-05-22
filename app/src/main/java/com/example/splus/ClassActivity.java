package com.example.splus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.splus.my_adapter.LessonAdapter;
import com.example.splus.my_data.ClassData;
import com.example.splus.my_data.LessonData;

import java.util.ArrayList;
import java.util.List;

public class ClassActivity extends AppCompatActivity {

    TextView className;

    ImageButton buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        ClassData myClass = (ClassData) bundle.get("class_data");

        System.out.println(myClass.getClassName());

        className = findViewById(R.id.textClassNameClassAct);
        className.setText(myClass.getClassName());

        RecyclerView myLesson = findViewById(R.id.listLessonClassAct);
        myLesson.setLayoutManager(new LinearLayoutManager(ClassActivity.this));

        LessonAdapter lessonAdapter = new LessonAdapter(getAllLesson(myClass), this::onClickGoToLesson);

        myLesson.setAdapter(lessonAdapter);

        buttonBack = findViewById(R.id.buttonBackClassAct);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void onClickGoToLesson(LessonData lessonData) {
        Intent intent = new Intent(ClassActivity.this, StudyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("lesson_data", lessonData);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @NonNull
    private List<LessonData> getAllLesson(@NonNull ClassData myClass) {
        List<LessonData> list = new ArrayList<>();

        LessonData lessonExample = new LessonData(
                R.drawable.splus_logo,
                "SPLUS-0000",
                getString(R.string.lesson_name_example),
                getString(R.string.lesson_content_example),
                myClass.getClassName(),
                myClass.getTeacherName()
        );

        list.add(lessonExample);

        list.add( new LessonData(
                R.drawable.splus_logo,
                "SPLUS-0001",
                "Giới thiệu môn học",
                "Môn Giải tích giúp cho người học có kiến thức cơ bản về phép tính vi phân hàm nhiều biến;" +
                        " phép tính tích phân hàm nhiều biến (tích phân bội); tích phân đường, tích phân mặt;" +
                        " cũng như là kỹ năng khảo sát chuỗi số, chuỗi hàm, tích phân suy rộng, …" +
                        " cùng với việc nhận dạng và giải quyết một số phương trình vi phân cấp một, cấp cao, …",
                myClass.getClassName(),
                myClass.getTeacherName())
        );

        list.add( new LessonData(
                R.drawable.splus_logo,
                "SPLUS-0002",
                "Giới thiệu môn học",
                "Môn học Đại số tuyến tính giúp cho sinh viên nắm được khái niệm và làm được các phép toán" +
                        " về ma trận, hạng, định thức, hệ phương trình tuyến tính; cách giải hệ phương trình tuyến tính" +
                        " bằng phương pháp Cramer, phương pháp Gauss, phương pháp Gauss-Jordan; về không gian vector," +
                        " sự phụ thuộc, độc lập tuyến tính, tập sinh, cơ sở và số chiều của không gian vector;" +
                        " ma trận chéo hóa và ý nghĩa của việc chéo hóa ma trận; về ánh xạ tuyến tính, toán tử tuyến tính," +
                        " dạng toàn phương và phép đưa dạng toàn phương về dạng chính tắc; …",
                myClass.getClassName(),
                myClass.getTeacherName())
        );

        return list;
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        finish();
    }
}