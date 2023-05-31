package com.example.splus.my_adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splus.R;
import com.example.splus.my_data.Question;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    private final List<Question> questionList;

    public QuestionAdapter(List<Question> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuestionAdapter.QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new QuestionAdapter.QuestionViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);
        if (question == null) {
            return;
        }
        QuestionAdapter.QuestionViewHolder questionViewHolder = (QuestionAdapter.QuestionViewHolder) holder;
        questionViewHolder.numberQuestion.setText("Câu" + question.getNumber());
        questionViewHolder.question.setText(question.getQuestion());
        questionViewHolder.answerA.setText(question.getAnswerA());
        questionViewHolder.answerB.setText(question.getAnswerB());
        questionViewHolder.answerC.setText(question.getAnswerC());
        questionViewHolder.answerD.setText(question.getAnswerD());
    }

    @Override
    public int getItemCount() {
        if (questionList != null) {
            return questionList.size();
        }
        return 0;
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {

        private final TextView numberQuestion;
        private final TextView question;
        private final RadioButton answerA, answerB, answerC, answerD;

        public QuestionViewHolder(@NonNull View view) {
            super(view);
            numberQuestion = view.findViewById(R.id.textNumberItemQuestion);
            question = view.findViewById(R.id.textContentItemQuestion);
            answerA = view.findViewById(R.id.radioFirstItemQuestion);
            answerB = view.findViewById(R.id.radioSecondItemQuestion);
            answerC = view.findViewById(R.id.radioThirdItemQuestion);
            answerD = view.findViewById(R.id.radioLastItemQuestion);
        }
    }
}
