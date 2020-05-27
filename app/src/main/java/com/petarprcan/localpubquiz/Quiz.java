package com.petarprcan.localpubquiz;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private String quizTitle;
    private List<Question> questions;
    private int id;

    public Quiz(){}

    public Quiz(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public Quiz(String quizTitle, List<Question> questions) {
        this.quizTitle = quizTitle;
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question q){
        if(this.questions == null){
            this.questions = new ArrayList<>();
        }

        this.questions.add(q);
    }

    public void removeQuestion(int index){
        this.questions.remove(index);
    }

    public boolean hasQuestions(){
        if (this.questions.size() > 0){
            return true;
        }
        return false;
    }


}
