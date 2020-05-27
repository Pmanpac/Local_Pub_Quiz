package com.petarprcan.localpubquiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MojiKvizovi extends AppCompatActivity {

    private List<Quiz> quizList;
    private RecyclerView recyclerView;
    private QuizAdapter quizAdapter;
    private QuizDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moji_kvizovi);

        // Spoji se na bazu i dohvati sve kvizove
        dbHelper = new QuizDBHelper(this);

    }


    @Override
    protected void onResume() {
        super.onResume();

        quizList = this.dbHelper.getAllQuiz();


        // Inicijalizacija RecyclerView za listu kvizova
        recyclerView = findViewById(R.id.listaKvizova);
        quizAdapter = new QuizAdapter(this, quizList);
        recyclerView.setAdapter(quizAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            quizAdapter.notifyDataSetChanged();
        }
    }
}
