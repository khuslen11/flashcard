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

        String order = "random";

        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("--order")) {
                if (i + 1 < args.length) {
                    order = args[i + 1];
                    i++;
                } else {
                    System.out.println("Error: --order requires a value.");
                    return;
                }
            } else if (args[i].equals("--help")) {
                printHelp();
                return;
            }
        }

        System.out.println("Flashcard App starting...");

        String cardsFile = args[0]; // first argument is the filename
        List<Flashcard> flashcards = readFlashcardsFromFile(cardsFile);
        System.out.println("Loaded " + flashcards.size() + " flashcards.");

        if (order.equals("random")) {
            Collections.shuffle(flashcards);
        }
        runQuiz(flashcards);
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

    private static void runQuiz(List<Flashcard> flashcards) {
        Scanner scanner = new Scanner(System.in);
        int correctAnswers = 0;

        for (Flashcard card : flashcards) {
            System.out.println("Question: " + card.getQuestion());
            System.out.print("Your answer: ");
            String userAnswer = scanner.nextLine();

            if (userAnswer.trim().equalsIgnoreCase(card.getAnswer().trim())) {
                System.out.println("Correct!");
                correctAnswers++;
            } else {
                System.out.println("Wrong! Correct answer: " + card.getAnswer());
            }
            System.out.println();
        }

        System.out.println("Quiz finished!");
        System.out.println("Correct answers: " + correctAnswers + " out of " + flashcards.size());
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