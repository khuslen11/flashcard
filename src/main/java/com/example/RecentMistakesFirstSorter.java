package com.example;

import java.util.ArrayList;
import java.util.List;

public class RecentMistakesFirstSorter implements CardOrganizer {

    private List<Flashcard> mistakes = new ArrayList<>();

    public void addMistake(Flashcard card) {
        if (!mistakes.contains(card)) {
            mistakes.add(card);
        }
    }

    public void clearMistakes() {
        mistakes.clear();
    }

    @Override
    public List<Flashcard> organize(List<Flashcard> cards) {
        List<Flashcard> ordered = new ArrayList<>();
        for (Flashcard card : cards) {
            if (mistakes.contains(card)) {
                ordered.add(card);
            }
        }
        for (Flashcard card : cards) {
            if (!mistakes.contains(card)) {
                ordered.add(card);
            }
        }
        return ordered;
    }
}
