package com.example;

import java.util.Objects;

public class Flashcard {
    private String question;
    private String answer;
    private int mistakes = 0;

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getMistakes() {
        return mistakes;
    }
    
    public void incrementMistakes() {
        mistakes++;
    }
    
    public void resetMistakes() {
        mistakes = 0;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flashcard)) return false;
        Flashcard that = (Flashcard) o;
        return Objects.equals(question, that.question) &&
               Objects.equals(answer, that.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answer);
    }
}
