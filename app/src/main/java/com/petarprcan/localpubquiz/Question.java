package com.petarprcan.localpubquiz;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String questionText;
    private List<Answer> answers;
    private int id;
    private long quizID;

    public Question(){}

    public Question(String questionText) {
        this.questionText = questionText;
    }

    public Question(String questionText, List<Answer> answers) {
        this.questionText = questionText;
        this.answers = answers;
    }

    public Question(String questionText, List<Answer> answers, int id, int quizID) {
        this.questionText = questionText;
        this.answers = answers;
        this.id = id;
        this.quizID = quizID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getQuizID() {
        return quizID;
    }

    public void setQuizID(long quizID) {
        this.quizID = quizID;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void addAnswer(Answer a){
        if(this.answers == null){
            this.answers = new ArrayList<>();
        }

        this.answers.add(a);
    }

    public void removeQuestion(int index){
        this.answers.remove(index);
    }
}
