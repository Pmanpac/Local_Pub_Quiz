package com.petarprcan.localpubquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuizGame extends AppCompatActivity {

    private int quizID;
    private TextView labelScore, labelQuestionCounter, labelQuizTitle, questionText;
    private Button btnConfirmAnswer;
    private RadioGroup radioGroup;

    private QuizDBHelper dbHelper;
    private int questionIndex = 0;
    private int score;

    private List<Question> questionList = new ArrayList<>();
    private Quiz quiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_game);

        Intent intent = getIntent();
        quizID = intent.getIntExtra("quizID", 1);

        labelScore = findViewById(R.id.quizGameScore);
        labelQuestionCounter = findViewById(R.id.quizGameQuestionNumber);
        labelQuizTitle = findViewById(R.id.quizGameTitle);
        questionText = findViewById(R.id.quizGameQuestionText);

        radioGroup = findViewById(R.id.quizGameRadioGroup);
        btnConfirmAnswer = findViewById(R.id.quizGameBtnConfirm);

        dbHelper = new QuizDBHelper(this);

        quiz = dbHelper.getQuizById(quizID);

        questionList = dbHelper.getQuizQuestions(quizID);

        loadQuestion();

        btnConfirmAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRadioButtonSelected()){
                    Toast.makeText(QuizGame.this, "Morate dobrati jedan od ponuđenih odgovora", Toast.LENGTH_SHORT).show();
                }

                int selectedRadioButtonIndex = getSelectedRadioButtonIndex();
                if(questionList.get(questionIndex).getAnswers().get(selectedRadioButtonIndex).isCorrect()){
                    Toast.makeText(QuizGame.this, "Točan odgovor", Toast.LENGTH_SHORT).show();
                    score++;
                }else{
                    Toast.makeText(QuizGame.this, "Odgovor nije točan", Toast.LENGTH_SHORT).show();
                }

                questionIndex++;
                if(questionIndex < questionList.size()){
                    radioGroup.removeAllViews();
                    loadQuestion();
                }else{
                    finish();
                }
            }
        });

    }

    private void loadQuestion(){
        labelScore.setText("Bodovi: " + String.valueOf(score));
        labelQuestionCounter.setText("Pitanje broj: " + String.valueOf(questionIndex + 1) + "/" + String.valueOf(questionList.size()));
        labelQuizTitle.setText(quiz.getQuizTitle());
        questionText.setText(questionList.get(questionIndex).getQuestionText());

        List<Answer> answers = dbHelper.getQuestionAnswers(questionList.get(questionIndex).getId());
        questionList.get(questionIndex).setAnswers(answers);
        renderAnswers(answers);
    }

    public void renderAnswers(List<Answer> answers){
        for(Answer a : answers){
            RadioButton rb = new RadioButton(QuizGame.this);
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            rb.setLayoutParams(lparams);
            rb.setText(a.getAnswerText());
            radioGroup.addView(rb);
        }
    }

    private boolean isRadioButtonSelected(){
        if (radioGroup.getCheckedRadioButtonId() == -1){
            return false;
        }return true;
    }

    private int getSelectedRadioButtonIndex(){
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonId);
        int radioIndex = radioGroup.indexOfChild(radioButton);

        return radioIndex;
    }
}
