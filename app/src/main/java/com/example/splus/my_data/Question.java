package com.example.splus.my_data;

public class Question {
    private final int number;
    private final String question;
    private final String answerA, answerB, answerC, answerD;
    private final char answerKey;

    public Question(int number, String question, String answerA, String answerB, String answerC, String answerD, char answerKey) {
        this.number = number;
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.answerKey = answerKey;
    }

    public int getNumber() {
        return number;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public char getAnswerKey() {
        return answerKey;
    }
}
