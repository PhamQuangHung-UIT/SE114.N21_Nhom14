package com.example.splus.my_adapter;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.R;
import com.example.splus.my_data.Question;

import java.util.List;

public class SubmissionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<Question> listQuestion;
    public List<Integer> listSelection;

    @SuppressLint("NotifyDataSetChanged")
    public SubmissionAdapter(List<Question> listQuestion, List<Integer> listSelection) {
        this.listQuestion = listQuestion;
        this.listSelection = listSelection;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Question question = listQuestion.get(position);
        if (question == null) {
            return;
        }
        int selection = listSelection.get(position);

        QuestionViewHolder questionViewHolder = (QuestionViewHolder) holder;

        questionViewHolder.q_number.setText("Câu " + question.getNumber());
        questionViewHolder.q_content.setText(question.getQuestion());
        questionViewHolder.a_content.setText(question.getAnswerA());
        questionViewHolder.b_content.setText(question.getAnswerB());
        questionViewHolder.c_content.setText(question.getAnswerC());
        questionViewHolder.d_content.setText(question.getAnswerD());

        questionViewHolder.a_content.setSelected(false);
        questionViewHolder.b_content.setSelected(false);
        questionViewHolder.c_content.setSelected(false);
        questionViewHolder.d_content.setSelected(false);

        switch (selection) {
            case 0:
                questionViewHolder.a_content.setSelected(true);
                break;
            case 1:
                questionViewHolder.b_content.setSelected(true);
                break;
            case 2:
                questionViewHolder.c_content.setSelected(true);
                break;
            default:
                questionViewHolder.d_content.setSelected(true);
        }

        Drawable correct_answer = ((QuestionViewHolder) holder).q_content.getContext().getResources().getDrawable(R.drawable.baseline_check_circle_24);
        switch (question.getAnswerKey()) {
            case 'a':
                questionViewHolder.a_content.setCompoundDrawables(null, null, correct_answer, null);
                break;
            case 'b':
                questionViewHolder.b_content.setCompoundDrawables(null, null, correct_answer, null);
                break;
            case 'c':
                questionViewHolder.c_content.setCompoundDrawables(null, null, correct_answer, null);
                break;
            case 'd':
                questionViewHolder.d_content.setCompoundDrawables(null, null, correct_answer, null);
                break;
            default:
                // nothing
        }
    }

    @Override
    public int getItemCount() {
        if (listQuestion != null) {
            return listQuestion.size();
        }
        return 0;
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        private final TextView q_number;
        private final TextView q_content;
        private final TextView a_content;
        private final TextView b_content;
        private final TextView c_content;
        private final TextView d_content;

        public QuestionViewHolder(@NonNull View view) {
            super(view);
            q_number = view.findViewById(R.id.textNumberItemQuestion);
            q_content = view.findViewById(R.id.textContentItemQuestion);
            a_content = view.findViewById(R.id.radioFirstItemQuestion);
            b_content = view.findViewById(R.id.radioSecondItemQuestion);
            c_content = view.findViewById(R.id.radioThirdItemQuestion);
            d_content = view.findViewById(R.id.radioLastItemQuestion);
        }
    }

}
