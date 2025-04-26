package com.example;

import java.util.Objects;

public class Flashcard {
    private String question;
    private String answer;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flashcard)) return false;
        Flashcard flashcard = (Flashcard) o;
        return Objects.equals(question, flashcard.question) &&
               Objects.equals(answer, flashcard.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answer);
    }
}
