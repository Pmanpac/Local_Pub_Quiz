package com.petarprcan.localpubquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NovoPitanje extends AppCompatActivity {

    private EditText questionText;
    private RadioGroup radioGroup;
    private Button btnDodajOdgovor, btnSpremiPitanje, btnDodajNovoPitanje;
    private int quizID;
    private QuizDBHelper dbHelper;

    private List<CheckBox> radioButtonList = new ArrayList<>();
    private List<EditText> editTextList = new ArrayList<>();

    private int questionCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_pitanje);

        Intent intent = getIntent();
        quizID = intent.getIntExtra("quizID", 1);

        dbHelper = new QuizDBHelper(this);

        questionText = findViewById(R.id.inputNovoPitanje);
        radioGroup = findViewById(R.id.answersGroup);
        btnDodajOdgovor = findViewById(R.id.btnDodajOdgovor);
        btnSpremiPitanje = findViewById(R.id.btnSpremiPitanje);


        final LinearLayout linearLayout = new LinearLayout(NovoPitanje.this);
        CheckBox rb = new CheckBox(NovoPitanje.this);
        EditText et = new EditText(NovoPitanje.this);

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setLayoutParams(lparams);
        et.setLayoutParams(lparams);

        lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rb.setLayoutParams(lparams);
        rb.setId(questionCounter);

        questionCounter ++;

        linearLayout.addView(rb);
        radioButtonList.add(rb);
        linearLayout.addView(et);
        editTextList.add(et);

        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    for (CheckBox c : radioButtonList){
                        c.setChecked(false);
                    }

                    buttonView.setChecked(true);
                }
            }
        });

        radioGroup.addView(linearLayout);



        btnSpremiPitanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkQuestionText()){
                    Toast.makeText(NovoPitanje.this, "Morate unjeti tekst pitanja", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!checkIfOneCorrect()){
                    Toast.makeText(NovoPitanje.this, "Mora bit točno jedan točan odgovor", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!checkAnswerText()){
                    Toast.makeText(NovoPitanje.this, "Svaki odgovor Mora imati text. Dodajte text ili izbrišite odgovor", Toast.LENGTH_LONG).show();
                    return;
                }

                Question question = new Question(questionText.getText().toString().trim());
                question.setQuizID(quizID);

                List<Answer> answers = new ArrayList<>();

                for(int x = 0; x < editTextList.size(); x++){
                    Answer a = new Answer(editTextList.get(x).getText().toString().trim(), radioButtonList.get(x).isChecked());
                    answers.add(a);
                }

                question.setAnswers(answers);

                dbHelper.addQuestion(question);

                Toast.makeText(NovoPitanje.this, "Pitanje dodano", Toast.LENGTH_LONG).show();

                finish();

            }
        });

        btnDodajOdgovor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionCounter >= 4) {
                    Toast.makeText(NovoPitanje.this, "Pitanje ne moze imati vise od 4 odgovora", Toast.LENGTH_SHORT).show();
                } else {
                    final LinearLayout linearLayout = new LinearLayout(NovoPitanje.this);
                    CheckBox rb = new CheckBox(NovoPitanje.this);
                    EditText et = new EditText(NovoPitanje.this);
                    ImageView iv = new ImageView(NovoPitanje.this);

                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    linearLayout.setLayoutParams(lparams);
                    et.setLayoutParams(lparams);

                    lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    rb.setLayoutParams(lparams);
                    rb.setId(questionCounter);
                    questionCounter ++;

                    lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    iv.setLayoutParams(lparams);
                    iv.setImageResource(R.drawable.delete_ic_24);

                    linearLayout.addView(iv);
                    linearLayout.addView(rb);
                    radioButtonList.add(rb);
                    linearLayout.addView(et);
                    editTextList.add(et);

                    rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked){
                                for (CheckBox c : radioButtonList){
                                    c.setChecked(false);
                                }

                                buttonView.setChecked(true);
                            }
                        }
                    });

                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            linearLayout.setVisibility(LinearLayout.GONE);
                            radioButtonList.get(questionCounter - 1).setChecked(false);
                            radioButtonList.remove(radioButtonList.get(questionCounter - 1));
                            editTextList.get(questionCounter - 1).setText("");
                            editTextList.remove(editTextList.get(questionCounter - 1));
                            questionCounter--;
                        }
                    });

                    radioGroup.addView(linearLayout);
                }

            }
        });

    }

    private boolean checkQuestionText() {
        if (questionText.getText().toString().trim().matches("")) {
            return false;
        }
        return true;
    }

    private boolean checkAnswerText(){
        for(EditText polje : editTextList){
            if (polje.getText().toString().trim().matches("")){
                return false;
            }
        }return true;
    }

    private boolean checkIfCorrect() {
        for(CheckBox r : radioButtonList){
            if (r.isChecked()){
                return true;
            }
        }

        return false;
    }

    private boolean checkIfOneCorrect() {
        int counter = 0;
        for(CheckBox r : radioButtonList){
            if (r.isChecked()){
                counter++;
            }
        }

        if (counter != 1){
            return false;
        }

        return true;
    }

    private int getSelectedRadioButtonIndex() {
        int index;

        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        index = radioGroup.indexOfChild(radioButton);

        return index;
    }

    private RadioButton getRadioButtonAtIndex(int index) {
        RadioButton r = (RadioButton) radioGroup.getChildAt(index);
        return r;
    }


}
