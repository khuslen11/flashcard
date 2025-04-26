package com.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WorstFirstSorter implements CardOrganizer {

    @Override
    public List<Flashcard> organize(List<Flashcard> flashcards) {
        List<Flashcard> sortedList = new ArrayList<>(flashcards);
        sortedList.sort(Comparator.comparingInt(Flashcard::getMistakes).reversed());
        return sortedList;
    }
}
