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

import com.example.splus.my_interface.IClickItemListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.splus.R;

<<<<<<< HEAD:app/src/main/java/com/example/splus/HomeFragment.java
=======
public class AssignFragment extends Fragment {
>>>>>>> acd84aaf57af25db1b445cfffe11a67e171e5efc:app/src/main/java/com/example/splus/my_fragment/AssignFragment.java
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recentLesson = view.findViewById(R.id.recentLessonList);
        recentLesson.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemAdapter itemAdapter = new ItemAdapter(getListItem(), this::onClickGoToDetail);

        recentLesson.setAdapter(itemAdapter);
        return view;
    }

    private List<ItemData> getListItem() {
        List<ItemData> list = new ArrayList<>();
        list.add(new ItemData(R.drawable.splus_logo, "MA006.K17", "Calculus", "Mr. Le Hoang Tuan"));
        list.add(new ItemData(R.drawable.splus_logo, "MA003.K17", "Linear Algebra", "Mr. Duong Ngoc Hao"));
        list.add(new ItemData(R.drawable.splus_logo, "MA004.K24", "Discrete structures", "Ms. Le Huynh My Van"));
        list.add(new ItemData(R.drawable.splus_logo, "MA005.L25", "Probability and statistics", "Mr. Nguyen Huu Hieu"));
        return list;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void onClickGoToDetail(ItemData item) {
        Intent intent = new Intent(this.getActivity(), StudyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_item", item);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
