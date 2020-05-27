package com.petarprcan.localpubquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NoviKviz extends AppCompatActivity {

    private Quiz quiz;
    private Button btnSaveQuiz;
    private EditText inputQuizTitle;
    private QuizDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novi_kviz);

        // Baza
        dbHelper = new QuizDBHelper(this);

        // Dohvati graficke komponente
        btnSaveQuiz = findViewById(R.id.btnSaveQuiz);
        inputQuizTitle = findViewById(R.id.inputQuizTitle);

        // Spremi quiz

        btnSaveQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkQuizName()){
                    Toast.makeText(NoviKviz.this, "Da bi ste spremili kviz morate prvo unjeti naziv kviza", Toast.LENGTH_LONG).show();
                }else{
                    quiz = new Quiz(inputQuizTitle.getText().toString().trim());
                    if (dbHelper.isQuizTitleUnique(quiz.getQuizTitle())){
                        dbHelper.addQuiz(quiz);
                        Toast.makeText(NoviKviz.this, "Dodan novi kviz", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(NoviKviz.this, "Naziv kviza mora bit unikatan", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }

    private boolean checkQuizName(){
        if (inputQuizTitle.getText().toString().trim().matches("")){
            return false;
        }

        return true;
    }
}
