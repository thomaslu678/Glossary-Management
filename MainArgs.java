package comprehensive.early;
//package comprehensive;

import comprehensive.Glossary;

public class MainArgs {

    // javac comprehensive/files/Main.java

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("No input glossary file path was given, cannot construct glossary.");
        }
        else {
            Glossary glossary = new Glossary();

            boolean validFilepath = false;
            boolean outOfBound = false;

            int count = 0;

            while (!validFilepath) {

                if (count == args.length) {
                    validFilepath = true;
                    outOfBound = true;
                }
                else {

                    try {
                        System.out.print("Enter a valid file path: " + args[count]);
                        glossary.populateGlossary(args[count]);
                        validFilepath = true;
                    } catch (Exception e) {
                        System.out.println("\nInvalid file path, please try again.");
                        count++;
                    }

                }
            }

            if (!outOfBound) {

                count += 1; // should never be reached, all input afterwards should be from Scanner

                while (count < args.length) {

                    System.out.print("\nGive input: " + args[count]);
                    String input = args[count];

                    switch (input) {
                        case "3" -> {
                            String word;

                            try {
                                count++;
                                word = args[count];
                                System.out.println("\n" + glossary.option3(word, true));
                            } catch (Exception e) {
                                System.out.println("\nYou did not pass a word to read! (or some other programmer error)");
                            }

                        }
                        case "11" -> {
                            count = args.length;
                        }
                        default -> {
                            System.out.println("\nNot yet handled");
                        }
                    }

                    count++;
                }

            }

        }

    }

}
