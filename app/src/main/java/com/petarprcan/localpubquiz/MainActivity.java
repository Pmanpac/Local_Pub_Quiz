package com.petarprcan.localpubquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button btnQuizList, btnNewQuiz, btnFindNewGame;
    EditText usernameLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Dohvati gumbove i polje za unos texta (korisnicko ime)
        btnQuizList = findViewById(R.id.btnKvizovi);
        btnNewQuiz = findViewById(R.id.btnNoviKviz);
        btnFindNewGame = findViewById(R.id.btnPrijava);

        usernameLabel = findViewById(R.id.inputKorisnickoIme);

        // Dodaj funkcijonalnosti gumbovima
        btnQuizList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MojiKvizovi.class);
                startActivity(intent);
            }
        });

        // Dodaj funkcijonalnosti gumbovima
        btnNewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoviKviz.class);
                startActivity(intent);
            }
        });
    }
}
