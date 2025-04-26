package com.example;

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

        System.out.println("Flashcard App starting...");
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