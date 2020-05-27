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

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {

    Context context;
    List<Quiz> quizList;

    public QuizAdapter(Context context, List<Quiz> quizList){
        this.context = context;
        this.quizList = quizList;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.quiz_list_row, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {

        final Quiz current = quizList.get(position);

        holder.quizTitleLabel.setText(current.getQuizTitle());

        holder.editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, IzmijeniKviz.class);
                intent.putExtra("quizID", current.getId());
                ((Activity)context).startActivityForResult(intent, 1);
            }
        });

        holder.playImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuizGame.class);
                intent.putExtra("quizID", current.getId());
                ((Activity)context).startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    public class QuizViewHolder extends RecyclerView.ViewHolder{

        TextView quizTitleLabel;
        ImageView playImage, editImage, deleteImage;

        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);

            quizTitleLabel = itemView.findViewById(R.id.quizTitleLabel);
            playImage = itemView.findViewById(R.id.imagePlay);
            editImage = itemView.findViewById(R.id.imageEdit);
            deleteImage = itemView.findViewById(R.id.imageDelete);
        }
    }
}
