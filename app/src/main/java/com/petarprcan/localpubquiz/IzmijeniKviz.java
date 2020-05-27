package com.petarprcan.localpubquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.List;

public class IzmijeniKviz extends AppCompatActivity {

    private long quizID;
    private QuizDBHelper dbHelper;
    private Quiz quiz;
    private List<Question> questionList;
    private EditText inputQuizTitle;
    private Button btnSave, btnNewQuestion;
    private RecyclerView recyclerView;
    private QuestionAdapter questionAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izmijeni_kviz);

        Intent intent = getIntent();
        quizID = intent.getIntExtra("quizID", 1);

        dbHelper = new QuizDBHelper(this);
        quiz = dbHelper.getQuizById(quizID);

        inputQuizTitle = findViewById(R.id.inputIzmjenaNazivaKviza);
        btnSave = findViewById(R.id.spremiNoviNazivKviza);
        btnNewQuestion = findViewById(R.id.btnDodajNovoPitanje);
        inputQuizTitle.setText(quiz.getQuizTitle());


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!quiz.getQuizTitle().matches(inputQuizTitle.getText().toString()) && !inputQuizTitle.getText().toString().matches("")){
                    if (dbHelper.isQuizTitleUnique(inputQuizTitle.getText().toString())){
                        dbHelper.updateQuizTitle(quiz.getId(), inputQuizTitle.getText().toString());
                        Toast.makeText(IzmijeniKviz.this, "Izmijenjen naziv kviza", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(IzmijeniKviz.this, "Naziv kviza mora bit unikatan", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(IzmijeniKviz.this, "Novi naziv se mora razlikovati od starog i ne smije biti prazn", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnNewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IzmijeniKviz.this, NovoPitanje.class);
                intent.putExtra("quizID", quiz.getId());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        questionList = dbHelper.getQuizQuestions(quiz.getId());
        quiz.setQuestions(questionList);

        recyclerView = findViewById(R.id.questionsList);
        questionAdapter = new QuestionAdapter(this, quiz.getQuestions());
        recyclerView.setAdapter(questionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
