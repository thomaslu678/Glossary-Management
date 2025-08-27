package comprehensive;

/**
 * This class represents an entry in the glossary, where a word
 * can have multiple entries that contain a part of speech and a definition.
 *
 * @author Peter Giolas and Thomas Lu
 * @version 12/06/2024
 */
public class Entry implements Comparable<Entry> {

    private String partOfSpeech;
    private String definition;

    /**
     * Constructs a new Entry
     * @param partOfSpeech -- the new Entry's part of speech
     * @param definition -- the new Entry's definition
     */
    public Entry(String partOfSpeech, String definition) {
        this.partOfSpeech = partOfSpeech;
        this.definition = definition;
    }

    /**
     * By implementing Comparable, we are able to print the entries
     * for a given word in sorted order
     * @param o the object to be compared.
     * @return -- the comparison value between two entries
     */
    @Override
    public int compareTo(Entry o) {
        if (this.partOfSpeech.equals(o.partOfSpeech)) {
            return this.definition.compareTo(o.definition);
        }
        else {
            return this.partOfSpeech.compareTo(o.partOfSpeech);
        }
    }

    /**
     * Getter for the Entry's part of speech
     * @return -- the Entry's part of speech
     */
    public String getPartOfSpeech() {
        return partOfSpeech;

    }

    /**
     * Getter for the Entry's definition
     * @return -- the Entry's definition
     */
    public String getDefinition() {
        return definition;
    }

    /**
     * Setter for the Entry's definition
     * @param definition -- the new definition
     */
    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
