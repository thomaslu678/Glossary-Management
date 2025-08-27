package comprehensive.early;
//package comprehensive;

import comprehensive.Glossary;

import java.util.Scanner;

public class MainScanner {

    // javac comprehensive/files/Main.java

    public static void main(String[] args) {

        Glossary glossary = new Glossary();

        boolean validFilepath = false;

        String input;

        while (!validFilepath) {

            try {
                System.out.print("Enter a valid file path: ");
                input = new Scanner(System.in).nextLine();

                glossary.populateGlossary(input.trim());
                validFilepath = true;
            } catch (Exception e) {
                System.out.println("\nInvalid file path, please try again.");
            }

        }


        boolean running = true;

        while (running) {

            System.out.print("\nGive input: ");
            input = new Scanner(System.in).nextLine();

            input = input.trim();

            switch (input) {
                case "3" -> {
                    try {
                        System.out.print("\nInput your word: ");
                        input = new Scanner(System.in).nextLine();
                        System.out.println("\n" + glossary.option3(input.trim(), true));
                    } catch (Exception e) {
                        System.out.println("\nYou did not pass a word to read! (or some other programmer error)");
                    }

                }
                case "11" -> {
                    running = false;
                }
                default -> {
                    System.out.println("\nNot yet handled");
                }
            }

        }

    }

}
