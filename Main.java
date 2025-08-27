package comprehensive;

import java.io.*;
import java.util.*;

/**
 * The Main runner class that enables the end user
 * to interact with our implemented Glossary
 *
 * @author Peter Giolas and Thomas Lu
 * @version 12/06/2024
 */
public class Main {

    /**
     * The main method for this class
     * @param args -- the list of arguments provided by the end user,
     *             which should represent all attempts at giving a valid
     *             file path that contains the data to populate the glossary with
     */
    public static void main(String[] args){
        boolean running = (args.length != 0);
        Glossary glossary = null;
        String validPartsOfSpeech = "[noun, verb, adj, adv, pron, prep, conj, interj]";

        // Attempts to populate the glossary using the arguments passed to the main method
        if (running) {
            glossary = new Glossary();
            boolean validFilePath = false;
            int argsIndex = 0;

            while (!validFilePath) {

                if (argsIndex == args.length) {
                    running = false;
                    break;
                } else {
                    try {
                        System.out.print("Enter a valid file path: " + args[argsIndex]);
                        glossary.populateGlossary(args[argsIndex]);
                        validFilePath = true;
                        System.out.println();
                    } catch (Exception e) {
                        System.out.println("\nInvalid file path, please try again.");
                        argsIndex++;
                    }

                }

            }

        }

        // Check to see if glossary has been populated
        while (running) {
            String input = getTrimmedNonWhitespaceInput("Select an option: ");

            switch (input) {
                case "1" -> System.out.println("\n" + glossary.option1());
                case "2" -> {

                    String first = getTrimmedNonWhitespaceInput("Starting word: ");
                    String end = getTrimmedNonWhitespaceInput("Ending word: ");

                    try {
                        System.out.println("\n" + glossary.option2(first, end));
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println("\nThe input starting and/or ending word was invalid.");
                    }
                }
                case "3" -> {
                    String word = getTrimmedNonWhitespaceInput("Enter a word: ");
                    System.out.println("\n" + glossary.option3(word, true));
                }
                case "4" -> System.out.println("\n" + glossary.option4());
                case "5" -> System.out.println("\n" + glossary.option5());
                case "6" -> {
                    String word = getTrimmedNonWhitespaceInput("Enter a word: ");
                    System.out.println("\n" + glossary.option6(word));
                }
                case "7" -> {
                    String word = getTrimmedNonWhitespaceInput("Enter a word: ");

                    SortedArrayList<Entry> wordEntries = glossary.getGlossary().get(word);

                    if (wordEntries == null) {
                        System.out.println("\n" + word + " was not found in the glossary.");
                        break;
                    }

                    System.out.println("\n" + glossary.option3(word, false));

                    boolean invalidInput = true;
                    int numToUpdate = -1;
                    int glossarySize = glossary.getGlossary().get(word).size();
                    while (invalidInput) {
                        try {
                            numToUpdate = Integer.parseInt(
                                    getTrimmedNonWhitespaceInput("\n" + "Select a definition to update: ")
                            );

                            if (numToUpdate > 0 && numToUpdate <= glossarySize + 1) {
                                invalidInput = false;
                            } else {
                                System.out.println("\nInput number was not a valid choice to update.");
                            }
                        } catch (Exception e) {
                            System.out.println("\nInput was not a number to update.");
                        }

                    }

                    // User has entered a value definition to update = not the return to menu option
                    if (numToUpdate != wordEntries.size() + 1) {
                        String newDefinition = getTrimmedNonWhitespaceInput("Type a new definition: ");
                        System.out.println("\n" + glossary.option7(wordEntries, numToUpdate, newDefinition));

                    }

                }
                case "8" -> {
                    String word = getTrimmedNonWhitespaceInput("Enter a word: ");

                    String result = glossary.option3(word, false);

                    System.out.println("\n" + result);
                    
                    if(!result.equals(String.format("%s not found", word)))
                    {

                        boolean invalidInput = true;
                        int numToRemove = -1;
                        int glossarySize = glossary.getGlossary().get(word).size();
                        while (invalidInput) {
                            try {
                                numToRemove = Integer.parseInt(
                                        getTrimmedNonWhitespaceInput("\n" + "Select a definition to update: ")
                                );

                                if (numToRemove > 0 && numToRemove <= glossarySize + 1) {
                                    invalidInput = false;
                                } else {
                                    System.out.println("\nInput number was not a valid choice to update.");
                                }
                            } catch (Exception e) {
                                System.out.println("\nInput was not a number to update.");
                            }

                        }

                        String deleteResult = glossary.option8(word, numToRemove);

                        System.out.println("\n" + deleteResult);
                    }
                }
                case "9" -> {
                    String word = getTrimmedNonWhitespaceInput("Type a word: ");
                    System.out.printf("Valid parts of speech - %s", validPartsOfSpeech);

                    String partOfSpeech = null;
                    boolean invalidInput = true;

                    while(invalidInput) {
                        partOfSpeech = getTrimmedNonWhitespaceInput("\nEnter the part of speech: ");

                        if (validPartsOfSpeech.contains(partOfSpeech)) {
                            invalidInput = false;
                        }
                    }

                    String newDefinition = getTrimmedNonWhitespaceInput("Type a definition: ");
                    System.out.println("\n" + glossary.option9(word, partOfSpeech, newDefinition));
                }
                case "10" -> {

                    String filename = null;
                    boolean invalidInput = true;
                    while (invalidInput) {
                        filename = getTrimmedNonWhitespaceInput("Type a filename with path: ");

                        File file = new File(filename);
                        if (file.getParentFile() != null && file.getParentFile().exists()) {
                            invalidInput = false;
                            try
                            {
                                file.createNewFile();
                            }
                            catch(IOException ex)
                            {
                                ex.printStackTrace();
                            }

                        } else {
                            System.out.println("\nFile not found\n");
                        }

                    }

                    try
                    {
                        System.out.println("\n" + glossary.option10(filename));
                    }
                    catch(FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                }
                case "11" -> running = false;
                default -> System.out.println("\nInvalid selection");

            }

            System.out.println();

        }

    }

    /**
     * Fetches user input and validates it until the user has given a valid
     * input that does not contain whitespace
     * @param prompt -- the prompt for the user asking for input
     * @return -- the validated input
     */
    private static String getTrimmedNonWhitespaceInput(String prompt) {
        boolean invalidInput = true;
        String input = null;

        while (invalidInput) {
            System.out.print(prompt);
            input = new Scanner(System.in).nextLine().trim();

            if (input.equals("")) {
                System.out.println("\nYour input was empty.\n");
            }
            else {
                invalidInput = false;
            }
        }

        return input;
    }

}
