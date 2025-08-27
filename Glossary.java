package comprehensive;

import java.io.*;
import java.util.*;

/**
 * This class represents a glossary that contains
 * words and associated entries, where each Entry
 * contains a part of speech and a definition
 *
 * @author Peter Giolas and Thomas Lu
 * @version 12/06/2024
 */
public class Glossary {

    private TreeMap<String, SortedArrayList<Entry>> glossary;
    private int wordCount;
    private int defCount;
    private HashMap<String, Integer> pos;

    /**
     * Constructs a new glossary
     */
    public Glossary() {
        this.glossary = new TreeMap<>();
        this.pos = new HashMap<>();
    }

    /**
     * Getter for the glossary
     * @return -- the glossary
     */
    public TreeMap<String, SortedArrayList<Entry>> getGlossary() {
        return glossary;
    }

    /**
     * Populates the glossary from a given file path
     * @param filename -- the given file path
     * @throws IOException -- if the given file path is invalid
     */
    public void populateGlossary(String filename) throws IOException {

        BufferedReader fileReader = new BufferedReader(new FileReader(filename));
        String nextLine;

        while ((nextLine = fileReader.readLine()) != null) {

            // Parse the word from the input line
            int indexOfColon = nextLine.indexOf(':');
            String word = nextLine.substring(0, indexOfColon);
            nextLine = nextLine.substring(indexOfColon + 2);

            // Parse the part of speech from the input line
            indexOfColon = nextLine.indexOf(':');
            String partOfSpeech = nextLine.substring(0, indexOfColon);

            // Parse the definition from the input line
            String definition = nextLine.substring(indexOfColon + 2);

            // Add the word and new Entry as needed, while updating counters for number of words, etc.
            Entry newEntry = new Entry(partOfSpeech, definition);
            Integer count = pos.get(partOfSpeech);
            pos.put(partOfSpeech, count != null ? count + 1 : 1);
            SortedArrayList<Entry> entries = glossary.get(word);
            if (entries == null) {
                entries = new SortedArrayList<>();
                glossary.put(word, entries);
                wordCount++;
            }
            entries.insert(newEntry);

            defCount++;
        }
    }

    /**
     * Gets the metadata for the glossary
     * @return -- the metadata
     */
    public String option1() {

        String firstKey = " ";
        String lastKey = " ";
        double avg = 0;

        if (!glossary.isEmpty()) {
            firstKey = glossary.firstKey();
            lastKey = glossary.lastKey();
        }

        if (wordCount != 0) {
            avg = ((double) defCount) / (wordCount);
        }

        return String.format("words - %d\ndefinitions - %d\ndefinitions per word - %.3f\nparts of speech - %d\nfirst word - %s\nlast word - %s",
                wordCount, defCount, avg, pos.size(), firstKey, lastKey);

    }

    /**
     * Gets all words between a given starting and end word
     * @param start -- the starting word
     * @param end -- the end word
     * @return all words between the starting and end word
     */
    public String option2(String start, String end) {
        StringBuilder returnString = new StringBuilder(String.format("The words between %s and %s are - ", start, end));
        for (String key : glossary.subMap(start, true, end, true).keySet()) {
            returnString.append(String.format("\n       %s", key));
        }

        return returnString.toString();
    }

    /**
     * Gets all entries for a given word.
     * This method is also used for option 7 (update)
     * and option 8 (delete) to enable the user to pick
     * which definition to work with.
     * @param word -- the word to find all entries for
     * @param standalone -- whether the output is part of an option 7 or 8 input
     * @return -- all entries for a given word
     */
    public String option3(String word, boolean standalone) {
        StringBuilder returnString = new StringBuilder(String.format("Definitions for %s", word));

        try {
            int count = 1;

            SortedArrayList<Entry> SortedArrayList = glossary.get(word);

            if (SortedArrayList == null) {
                return String.format("%s not found", word);
            }

            for (Entry entry : SortedArrayList) {
                returnString.append(String.format("\n     %d. %s.      %s", count, entry.getPartOfSpeech(), entry.getDefinition()));
                count++;
            }

            if (!standalone) {
                returnString.append(String.format("\n     %d. Back to main menu", count));
            }
        }
        catch (Exception ignored) {

        }

        return returnString.toString();
    }

    /**
     * Gets all entries for the first word in the glossary
     * @return -- all entries for the first word in the glossary
     */
    public String option4() {
        if (glossary.isEmpty()) {
            return "The glossary is empty.";
        }
        else {

            StringBuilder returnString = new StringBuilder(glossary.firstKey());
            for (Entry entry : glossary.firstEntry().getValue()) {
                returnString.append(String.format("\n       %s.     %s", entry.getPartOfSpeech(), entry.getDefinition()));
            }

            return returnString.toString();
        }
    }

    /**
     * Gets all entries for the last word in the glossary
     * @return -- all entries for the last word in the glossary
     */
    public String option5() {

        if (glossary.isEmpty()) {
            return "The glossary is empty.";
        }
        else {

            StringBuilder returnString = new StringBuilder(glossary.lastKey());
            for (Entry entry : glossary.lastEntry().getValue()) {
                returnString.append(String.format("\n       %s.     %s", entry.getPartOfSpeech(), entry.getDefinition()));
            }

            return returnString.toString();
        }
    }

    /**
     * Gets all unique parts of speech for a given word
     * @param word -- the given word
     * @return -- all parts of speech
     */
    public String option6(String word) {
        SortedArrayList<Entry> entries = glossary.get(word);

        if (entries == null) {
            return word + " was not found in the glossary";
        }

        SortedArrayList<String> partsOfSpeech = new SortedArrayList<>();
        for (Entry entry : entries) {
            partsOfSpeech.insert(entry.getPartOfSpeech());
        }

        StringBuilder returnString = new StringBuilder(word);
        for (String part : partsOfSpeech) {
            returnString.append(String.format("\n       %s", part));
        }

        return returnString.toString();

    }

    /**
     * Updates a definition for a given word, selecting from all definitions for the word
     * @param entries -- all entries (containing the definitions) of a given word
     * @param numToUpdate -- the number (essentially index) of the definition to update
     * @param newDefinition -- the new definition
     * @return -- output information after updating the definition
     */
    public String option7(SortedArrayList<Entry> entries, int numToUpdate, String newDefinition) {

        Iterator<Entry> iterator = entries.iterator();
        Entry entry = null;
        for (int i = 0; i < numToUpdate; i++) {
            entry = iterator.next();
        }

        entry.setDefinition(newDefinition);

        return "Definition updated";
    }

    /**
     * Deletes a definition for a given word,
     * selecting from all definitions for the word.
     * If the deletion results in no definitions for
     * a word, it is deleted from the glossary.
     * @param word -- the word to delete a definition from
     * @param numToRemove -- the "index" of the definition to remove
     * @return -- log information after deleting the definition
     */
    public String option8(String word, int numToRemove) {

    	SortedArrayList<Entry> list = glossary.get(word);
        if(list == null)
        {
            return "Word doesn't exist";
        }
    	
        Entry entry = list.remove(numToRemove - 1);

        if(entry == null)
        {
            return "Definition doesn't exist";
        }
        
        defCount--;
        pos.put(entry.getPartOfSpeech(), pos.get(entry.getPartOfSpeech()) - 1);

        String returnString = "Definition removed";
        if (list.size() == 0) {
            returnString += String.format("\n%s removed", word);
            glossary.remove(word);
            wordCount --;
        }

        return returnString + "\n" + option3(word, true);
    }

    /**
     * Adds a new definition for a given word
     * @param word -- the given word
     * @param partOfSpeech -- the new Entry's part of speech
     * @param definition -- the new Entry's definition
     * @return -- log information after adding the new Entry
     */
    public String option9(String word, String partOfSpeech, String definition) {
        SortedArrayList<Entry> entries = glossary.get(word);
        if (entries == null) {
            entries = new SortedArrayList<>();
            glossary.put(word, entries);
            wordCount ++;
        }

        entries.insert(new Entry(partOfSpeech, definition));

        Integer count = pos.get(partOfSpeech);
        pos.put(partOfSpeech, count != null ? count + 1 : 1);
        defCount ++;

        return "Successfully added " + word;

    }

    /**
     * Saves the glossary (with any updates) to a given file path,
     * @param filename -- the file path and name of which is given by the user
     * @return -- log information after saving the glossary
     * @throws FileNotFoundException -- if the given file path and name does not exist
     */
    public String option10(String filename) throws FileNotFoundException {

        PrintWriter writer = new PrintWriter(filename);

        for (String word : glossary.keySet()) {
            SortedArrayList<Entry> entries = glossary.get(word);

            for (Entry entry : entries) {

                if (!entry.equals(glossary.firstEntry().getValue().min())) {
                    writer.print("\n");
                }

                writer.printf("%s::%s::%s", word, entry.getPartOfSpeech(), entry.getDefinition());
            }

        }

        writer.close();

        return String.format("Successfully saved dictionary to %s", filename);

    }

}
