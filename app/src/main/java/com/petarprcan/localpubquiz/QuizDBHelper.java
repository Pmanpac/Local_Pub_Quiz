package com.petarprcan.localpubquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.petarprcan.localpubquiz.QuizContract.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuizDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PubQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        // SQL naredbe za stvaranje tablica baze podataka

        final String CREATE_QUIZ_TABLE = "CREATE TABLE " + QuizTable.TABLE_NAME + " ( " +
                QuizTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizTable.QUIZ_NAME + " TEXT UNIQUE NOT NULL )";

        final String CREATE_QUESTION_TABLE = "CREATE TABLE " + QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.QUIZ_ID + " INTEGER NOT NULL, " +
                QuestionsTable.QUESTION_TEXT + " TEXT NOT NULL )";

        final String CREATE_ANSWER_TABLE = "CREATE TABLE " + AnswersTable.TABLE_NAME + " ( " +
                AnswersTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AnswersTable.QUESTION_ID + " INTEGER NOT NULL, " +
                AnswersTable.ANSWER_TEXT + " TEXT NOT NULL, " +
                AnswersTable.ANSWER_CORRECT + " INTEGER NOT NULL DEFAULT 0 CHECK(" + AnswersTable.ANSWER_CORRECT + " IN (0,1)) ) ";

        db.execSQL(CREATE_QUIZ_TABLE);
        db.execSQL(CREATE_QUESTION_TABLE);
        db.execSQL(CREATE_ANSWER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuizTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AnswersTable.TABLE_NAME);

        onCreate(db);
    }

    // Dohvat liste svih definiranih kvizova

    public List<Quiz> getAllQuiz() {
        List<Quiz> quizList = new ArrayList<>();

        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizTable.TABLE_NAME, null);
        Log.d("SQL BAZA DOHVAT KVIZOVA", "SELECT * FROM " + QuizTable.TABLE_NAME);

        while (c.moveToNext()){
            Quiz quiz = new Quiz();
            quiz.setId(c.getInt(c.getColumnIndex(QuizTable._ID)));
            quiz.setQuizTitle(c.getString(c.getColumnIndex(QuizTable.QUIZ_NAME)));
            quizList.add(quiz);
        }
        c.close();
        return quizList;
    }

    // Dohvati kviz s pomocu ID-a

    public Quiz getQuizById(long id){
        Quiz quiz = new Quiz();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizTable.TABLE_NAME + " WHERE " + QuizTable._ID + " = " + id, null);
        if (c.moveToFirst()){
            quiz.setId(c.getInt(c.getColumnIndex(QuizTable._ID)));
            quiz.setQuizTitle(c.getString(c.getColumnIndex(QuizTable.QUIZ_NAME)));
        }

        return quiz;
    }

    // Izmijene naslov kviza
    public void updateQuizTitle(long id, String newTitle){
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(QuizTable.QUIZ_NAME, newTitle);
        db.update(QuizTable.TABLE_NAME, cv, QuizTable._ID + "=" + id, null);
    }

    // Dodaje definirani kviz (listu pitanja tog kviza)

    public void addQuiz(Quiz q) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QuizTable.QUIZ_NAME, q.getQuizTitle());
        db.insert(QuizTable.TABLE_NAME, null, cv);
//        if (q.hasQuestions()) {
//            for (Question question : q.getQuestions()) {
//                question.setQuizID(quizID);
//                addQuestion(question);
//            }
//        }
    }

    // Dohvat liste svih definiranih pitanja odabranog kviza i listu odgovora za svako definirano pitanje

    public List<Question> getQuizQuestions(long quizID){
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME + " WHERE " + QuestionsTable.QUIZ_ID + " = " + quizID , null);

        while (c.moveToNext()){
            Question question = new Question();
            question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
            question.setQuizID(c.getInt(c.getColumnIndex(QuestionsTable.QUIZ_ID)));
            question.setQuestionText(c.getString(c.getColumnIndex(QuestionsTable.QUESTION_TEXT)));
            questionList.add(question);
        }
        c.close();
        return questionList;
    }

    // Dodaje definirano pitanje odabranom kvizu

    public void addQuestion(Question q) {
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.QUESTION_TEXT, q.getQuestionText());
        cv.put(QuestionsTable.QUIZ_ID, q.getQuizID());
        long questionID = db.insert(QuestionsTable.TABLE_NAME, null, cv);
        for (Answer a : q.getAnswers()) {
            a.setQuestionID(questionID);
            addAnswer(a);
        }
    }


    // Dohvati listu definiranih odgovora za odabrano pitanje

    public List<Answer> getQuestionAnswers(long questionID){
        List<Answer> answerList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + AnswersTable.TABLE_NAME + " WHERE " + AnswersTable.QUESTION_ID + " = " + questionID , null);

        if (c.moveToFirst()) {
            do {
                Answer answer = new Answer();
                answer.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                answer.setQuestionID(c.getInt(c.getColumnIndex(AnswersTable.QUESTION_ID)));
                answer.setAnswerText(c.getString(c.getColumnIndex(AnswersTable.ANSWER_TEXT)));
                answer.setCorrect(c.getInt(c.getColumnIndex(AnswersTable.ANSWER_CORRECT)) == 1 ? true : false);
                answerList.add(answer);

            } while (c.moveToNext());
        }
        c.close();
        return answerList;
    }

    // Dodaje definirano pitanje odabranom pitanju

    public void addAnswer(Answer a) {
        Log.d("THIS IS THE PART WHERE ANSWERS GO TO DB", a.getAnswerText());
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(AnswersTable.QUESTION_ID, a.getQuestionID());
        cv.put(AnswersTable.ANSWER_TEXT, a.getAnswerText());
        cv.put(AnswersTable.ANSWER_CORRECT, a.isCorrect() ? 1 : 0);
        long answerID = db.insert(AnswersTable.TABLE_NAME, null, cv);

    }

    public boolean isQuizTitleUnique(String title){

        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuizTable.TABLE_NAME  + " WHERE " + QuizTable.QUIZ_NAME + " = '" + title + "'", null);

        if (c.moveToFirst()){
            return false;
        }

        return true;

    }


}
