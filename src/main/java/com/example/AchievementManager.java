package com.example;

import java.util.HashMap;
import java.util.Map;

public class AchievementManager {
    private boolean allCorrect = true;
    private Map<Flashcard, Integer> attempts = new HashMap<>();
    private Map<Flashcard, Integer> correctCounts = new HashMap<>();

    public void recordAttempt(Flashcard card, boolean isCorrect) {
        attempts.put(card, attempts.getOrDefault(card, 0) + 1);
        if (isCorrect) {
            correctCounts.put(card, correctCounts.getOrDefault(card, 0) + 1);
        } else {
            allCorrect = false;
        }
    }

    public void showAchievements() {
        if (allCorrect) {
            System.out.println("🏆 Achievement unlocked: CORRECT (All cards answered correctly!)");
        }
        for (Map.Entry<Flashcard, Integer> entry : attempts.entrySet()) {
            if (entry.getValue() > 5) {
                System.out.println("🏆 Achievement unlocked: REPEAT (Card repeated more than 5 times!) - " + entry.getKey().getQuestion());
            }
        }
        for (Map.Entry<Flashcard, Integer> entry : correctCounts.entrySet()) {
            if (entry.getValue() >= 3) {
                System.out.println("🏆 Achievement unlocked: CONFIDENT (Card answered correctly at least 3 times!) - " + entry.getKey().getQuestion());
            }
        }
    }

    public void resetRound() {
        allCorrect = true;
    }
}
