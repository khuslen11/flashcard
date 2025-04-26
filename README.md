# FlashcardApp

Simple command-line based Flashcard Learning System built with Java and Maven.

## Features

- Load flashcards from a text file
- Command-line options to control the quiz:
  - `--order random|worst-first|recent-mistakes-first`
  - `--repetitions <num>`
  - `--invertCards`
- Track mistakes and repeat incorrect answers
- Simple and fast user interaction

## How to run

1. Build the project:

```bash
mvn clean package
