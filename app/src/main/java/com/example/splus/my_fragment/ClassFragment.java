package com.example.splus.my_fragment;

import android.annotation.SuppressLint;
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

import com.example.splus.ClassActivity;
import com.example.splus.R;
import com.example.splus.my_adapter.ClassAdapter;
import com.example.splus.my_data.ClassData;

import java.util.ArrayList;
import java.util.List;

public class ClassFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class, container, false);

        RecyclerView myClass = view.findViewById(R.id.recyclerListClassFragment);
        myClass.setLayoutManager(new LinearLayoutManager(getActivity()));

        ClassAdapter classAdapter = new ClassAdapter(getListClass(), this::onClickGoToClass);

        myClass.setAdapter(classAdapter);
        return view;
    }

    @NonNull
    private List<ClassData> getListClass() {
        List<ClassData> list = new ArrayList<>();

        ClassData classExample = new ClassData(
                R.drawable.splus_logo,
                getString(R.string.lesson_id_example),
                getString(R.string.class_name_example),
                getString(R.string.teacher_name_example)
        );

        list.add(classExample);

        list.add( new ClassData(
                R.drawable.splus_logo,
                "MA006.K17",
                "Giải tích",
                "ThS. Lê Hoàng Tuấn")
        );

        list.add( new ClassData(
                R.drawable.splus_logo,
                "MA003.K17",
                "Đại số tuyến tính",
                "TS. Dương Ngọc Hảo")
        );

        return list;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private void onClickGoToClass(ClassData myClass) {
        Intent intent = new Intent(this.getActivity(), ClassActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("class_data", myClass);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
