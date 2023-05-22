package com.example.splus.my_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.R;
import com.example.splus.my_data.ClassData;
import com.example.splus.my_interface.IClickClassListener;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.UserViewHolder> {

    private final List<ClassData> mListClass;
    private final IClickClassListener iClickClassListener;

    public ClassAdapter(List<ClassData> mListClass, IClickClassListener iClickClassListener) {
        this.mListClass = mListClass;
        this.iClickClassListener = iClickClassListener;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        ClassData myClass = mListClass.get(position);
        if (myClass == null) {
            return;
        }
        holder.className.setText(myClass.getClassName());
        holder.teacherName.setText(myClass.getTeacherName());

        holder.myClassItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickClassListener.onClickClass(myClass);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListClass != null) {
            return mListClass.size();
        }
        return 0;
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout myClassItem;
        private final TextView className;
        private final TextView teacherName;

        public UserViewHolder(@NonNull View myClassView) {
            super(myClassView);
            myClassItem = myClassView.findViewById(R.id.relativeItemClass);
            className = myClassView.findViewById(R.id.textClassNameItemClass);
            teacherName = myClassView.findViewById(R.id.textTeacherNameItemClass);
        }
    }
}
