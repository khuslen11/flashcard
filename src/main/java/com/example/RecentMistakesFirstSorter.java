package com.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecentMistakesFirstSorter implements CardOrganizer {
    private Set<Flashcard> mistakes = new HashSet<>();

    public void addMistake(Flashcard card) {
        mistakes.add(card);
    }

    public void clearMistakes() {
        mistakes.clear();
    }

    @Override
    public List<Flashcard> organize(List<Flashcard> flashcards) {
        List<Flashcard> result = new ArrayList<>();
        for (Flashcard card : flashcards) {
            if (mistakes.contains(card)) {
                result.add(card);
            }
        }
        for (Flashcard card : flashcards) {
            if (!mistakes.contains(card)) {
                result.add(card);
            }
        }
        return result;
    }
}
