package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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
        runQuiz(flashcards, repetitions);
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

    private static void runQuiz(List<Flashcard> flashcards, int repetitions) {
        Scanner scanner = new Scanner(System.in);
        int totalCorrectAnswers = 0;
        int totalQuestions = flashcards.size();

        for (Flashcard card : flashcards) {
            int correctCount = 0;
            while (correctCount < repetitions) {
                System.out.println("Question: " + card.getQuestion());
                System.out.print("Your answer: ");
                String userAnswer = scanner.nextLine();

                if (userAnswer.trim().equalsIgnoreCase(card.getAnswer().trim())) {
                    correctCount++;
                    System.out.println("Correct! (" + correctCount + "/" + repetitions + ")");
                } else {
                    System.out.println("Wrong! Correct answer: " + card.getAnswer());
                    correctCount = 0;
                }
                System.out.println();
            }
            totalCorrectAnswers++;
        }
        System.out.println("Quiz finished!");
        System.out.println("Total correct answers: " + totalCorrectAnswers + " out of " + totalQuestions);
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