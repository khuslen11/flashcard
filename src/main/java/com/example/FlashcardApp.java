package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FlashcardApp {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: No arguments provided.");
            System.out.println("Use --help for usage information.");
            return;
        }

        for (String arg : args) {
            if (arg.equals("--help")) {
                printHelp();
                return;
            }
        }

        String cardsFile = null;
        String order = "random";
        boolean invertCards = false;
        int repetitions = 1;

        int i = 0;
        if (!args[0].startsWith("--")) {
            cardsFile = args[0];
            i = 1;
        } else {
            System.out.println("Error: No cards file provided.");
            return;
        }

        for (; i < args.length; i++) {
            switch (args[i]) {
                case "--order":
                    if (i + 1 < args.length) {
                        order = args[++i];
                        if (!order.equals("random") && !order.equals("worst-first") && !order.equals("recent-mistakes-first")) {
                            System.out.println("Error: Invalid order type.");
                            return;
                        }
                    } else {
                        System.out.println("Error: --order requires a value.");
                        return;
                    }
                    break;
                case "--repetitions":
                    if (i + 1 < args.length) {
                        try {
                            repetitions = Integer.parseInt(args[++i]);
                        } catch (NumberFormatException e) {
                            System.out.println("Error: --repetitions must be a number.");
                            return;
                        }
                    } else {
                        System.out.println("Error: --repetitions requires a value.");
                        return;
                    }
                    break;
                case "--invertCards":
                    invertCards = true;
                    break;
                default:
                    System.out.println("Error: Unknown option " + args[i]);
                    return;
            }
        }

        System.out.println("Flashcard App starting...");

        List<Flashcard> flashcards = readFlashcardsFromFile(cardsFile);
        if (flashcards.isEmpty()) {
            System.out.println("No flashcards loaded. Exiting.");
            return;
        }

        if (invertCards) {
            for (Flashcard card : flashcards) {
                String temp = card.getQuestion();
                card.setQuestion(card.getAnswer());
                card.setAnswer(temp);
            }
        }
        if (order.equals("random")) {
            Collections.shuffle(flashcards);
        }
        runQuiz(flashcards, repetitions, order);
    }

    private static List<Flashcard> readFlashcardsFromFile(String filename) {
        List<Flashcard> flashcards = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String question;
            while ((question = reader.readLine()) != null) {
                String answer = reader.readLine();
                if (answer == null) {
                    System.out.println("Warning: Question without an answer: " + question);
                    break;
                }
                flashcards.add(new Flashcard(question, answer));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return flashcards;
    }

    private static void runQuiz(List<Flashcard> flashcards, int repetitions, String order) {
        Scanner scanner = new Scanner(System.in);

        RecentMistakesFirstSorter sorter = new RecentMistakesFirstSorter();
        boolean useRecentMistakes = order.equals("recent-mistakes-first");

        // Amjiltiin tracking
        Map<Flashcard, Integer> correctCounts = new HashMap<>();
        Map<Flashcard, Integer> totalAttempts = new HashMap<>();

        boolean allCorrect;
        do {
            allCorrect = true;

            List<Flashcard> quizCards;
            if (useRecentMistakes) {
                quizCards = sorter.organize(flashcards);
            } else {
                quizCards = new ArrayList<>(flashcards);
                if (order.equals("random")) {
                    Collections.shuffle(quizCards);
                }
            }

            for (Flashcard card : quizCards) {
                int correctCount = 0;
                while (correctCount < repetitions) {
                    System.out.println("Question: " + card.getQuestion());
                    System.out.print("Your answer: ");
                    String userAnswer = scanner.nextLine();

                    totalAttempts.put(card, totalAttempts.getOrDefault(card, 0) + 1);

                    if (userAnswer.trim().equalsIgnoreCase(card.getAnswer().trim())) {
                        correctCount++;
                        correctCounts.put(card, correctCounts.getOrDefault(card, 0) + 1);
                        System.out.println("Correct! (" + correctCount + "/" + repetitions + ")");
                        sorter.clearMistakes();
                    } else {
                        System.out.println("Wrong! Correct answer: " + card.getAnswer());
                        sorter.addMistake(card);
                        correctCount = 0;
                        allCorrect = false;
                    }
                    System.out.println();
                }
            }

            if (useRecentMistakes && !allCorrect) {
                System.out.println("Repeating mistakes first...");
            }

        } while (useRecentMistakes && !allCorrect);

        System.out.println("Quiz finished!");

        // Амжилтуудыг шалгах
        checkAchievements(correctCounts, totalAttempts);
    }

    private static void checkAchievements(Map<Flashcard, Integer> correctCounts, Map<Flashcard, Integer> totalAttempts) {
        boolean correctAchievement = correctCounts.values().stream().allMatch(count -> count > 0);
        boolean repeatAchievement = totalAttempts.values().stream().anyMatch(count -> count > 5);
        boolean confidentAchievement = correctCounts.values().stream().anyMatch(count -> count >= 3);

        System.out.println();
        System.out.println("Achievements:");
        if (correctAchievement) {
            System.out.println("- CORRECT: All flashcards were answered correctly!");
        }
        if (repeatAchievement) {
            System.out.println("- REPEAT: Answered some flashcards more than 5 times!");
        }
        if (confidentAchievement) {
            System.out.println("- CONFIDENT: Answered some flashcards correctly at least 3 times!");
        }
        if (!correctAchievement && !repeatAchievement && !confidentAchievement) {
            System.out.println("- No achievements unlocked. Keep practicing!");
        }
    }

    private static void printHelp() {
        System.out.println("Usage: flashcard <cards-file> [options]");
        System.out.println("Options:");
        System.out.println("--help                  Show help information");
        System.out.println("--order <order>         Order type: random, worst-first, recent-mistakes-first");
        System.out.println("--repetitions <num>     Number of correct repetitions required");
        System.out.println("--invertCards           Invert question and answer");
    }
}
