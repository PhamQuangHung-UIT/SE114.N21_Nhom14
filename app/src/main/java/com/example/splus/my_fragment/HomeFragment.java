package com.example.splus.my_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.R;
import com.example.splus.StudyActivity;
import com.example.splus.my_adapter.LessonAdapter;
import com.example.splus.my_data.LessonData;

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
        return view;
    }

    private List<LessonData> getRecentLesson() {
        List<LessonData> list = new ArrayList<>();

        LessonData lessonExample = new LessonData(
                R.drawable.splus_logo,
                getString(R.string.lesson_id_example),
                getString(R.string.lesson_name_example),
                getString(R.string.lesson_content_example),
                getString(R.string.class_name_example),
                getString(R.string.teacher_name_example)
        );

        list.add(lessonExample);

        list.add( new LessonData(
                R.drawable.splus_logo,
                "MA006.K17",
                "Giới thiệu môn học",
                "Môn Giải tích giúp cho người học có kiến thức cơ bản về phép tính vi phân hàm nhiều biến;" +
                        " phép tính tích phân hàm nhiều biến (tích phân bội); tích phân đường, tích phân mặt;" +
                        " cũng như là kỹ năng khảo sát chuỗi số, chuỗi hàm, tích phân suy rộng, …" +
                        " cùng với việc nhận dạng và giải quyết một số phương trình vi phân cấp một, cấp cao, …",
                "Giải tích",
                "ThS. Lê Hoàng Tuấn")
        );

        list.add( new LessonData(
                R.drawable.splus_logo,
                "MA003.K17",
                "Giới thiệu môn học",
                "Môn học Đại số tuyến tính giúp cho sinh viên nắm được khái niệm và làm được các phép toán" +
                        " về ma trận, hạng, định thức, hệ phương trình tuyến tính; cách giải hệ phương trình tuyến tính" +
                        " bằng phương pháp Cramer, phương pháp Gauss, phương pháp Gauss-Jordan; về không gian vector," +
                        " sự phụ thuộc, độc lập tuyến tính, tập sinh, cơ sở và số chiều của không gian vector;" +
                        " ma trận chéo hóa và ý nghĩa của việc chéo hóa ma trận; về ánh xạ tuyến tính, toán tử tuyến tính," +
                        " dạng toàn phương và phép đưa dạng toàn phương về dạng chính tắc; …",
                "Đại số tuyến tính",
                "TS. Dương Ngọc Hảo")
        );

        return list;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void onClickGoToLesson(LessonData lesson) {
        Intent intent = new Intent(this.getActivity(), StudyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("lesson_data", lesson);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
