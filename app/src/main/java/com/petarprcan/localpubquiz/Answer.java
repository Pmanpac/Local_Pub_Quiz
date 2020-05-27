package com.petarprcan.localpubquiz;

public class Answer {

    private String answerText;
    private boolean correct;
    private int id;
    private long questionID;

    public Answer(){}

    public Answer(String answerText, boolean correct) {
        this.answerText = answerText;
        this.correct = correct;
    }

    public Answer(String answerText, boolean correct, int id, int questionID) {
        this.answerText = answerText;
        this.correct = correct;
        this.id = id;
        this.questionID = questionID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(long questionID) {
        this.questionID = questionID;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
