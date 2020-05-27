package com.petarprcan.localpubquiz;

import android.provider.BaseColumns;

public class QuizContract {

    private QuizContract(){}

    public static class QuizTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz";
        public static final String QUIZ_NAME = "quiz_name";
    }

    public static class QuestionsTable implements BaseColumns{
        public static final String TABLE_NAME = "question";
        public static final String QUIZ_ID = "quiz_id";
        public static final String QUESTION_TEXT = "question_text";
    }

    public static class AnswersTable implements BaseColumns{
        public static final String TABLE_NAME = "answer";
        public static final String QUESTION_ID = "question_id";
        public static final String ANSWER_TEXT = "answer_text";
        public static final String ANSWER_CORRECT = "answer_correct";
    }
}
