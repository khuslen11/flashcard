package com.example;

import java.util.*;

public class WorstFirstSorter implements CardOrganizer {
    private Map<Flashcard, Integer> mistakeCounts = new HashMap<>();

    public void addMistake(Flashcard card) {
        mistakeCounts.put(card, mistakeCounts.getOrDefault(card, 0) + 1);
    }

    @Override
    public List<Flashcard> organize(List<Flashcard> flashcards) {
        List<Flashcard> sorted = new ArrayList<>(flashcards);
        sorted.sort((a, b) -> Integer.compare(
                mistakeCounts.getOrDefault(b, 0),
                mistakeCounts.getOrDefault(a, 0)
        ));
        return sorted;
    }
}
