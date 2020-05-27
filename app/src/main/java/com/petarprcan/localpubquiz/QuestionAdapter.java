package com.petarprcan.localpubquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    Context context;
    List<Question> questionList;

    public QuestionAdapter(Context context, List<Question> questionList){
        this.context = context;
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.question_list_row, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {

        final Question current = questionList.get(position);

        holder.questionTextLabel.setText(current.getQuestionText());

        holder.editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, IzmijeniKviz.class);
                intent.putExtra("quizID", current.getId());
                ((Activity)context).startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder{

        TextView questionTextLabel;
        ImageView playImage, editImage, deleteImage;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);

            questionTextLabel = itemView.findViewById(R.id.questionItemLabel);
            editImage = itemView.findViewById(R.id.imageEdit);
            deleteImage = itemView.findViewById(R.id.imageDelete);
        }
    }
}
