package comprehensive.timing;

import comprehensive.Entry;
import comprehensive.Glossary;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class MainTimingExperiment extends TimingExperiment {

    private static String problemSizeDescription = "numEntries";
    private static int problemSizeMin = 100;
    private static int problemSizeCount = 20;
    private static int problemSizeStep = 100;
    private static int experimentIterationCount = 1000;
    protected Glossary glossary;
    protected List<String> words;
    protected List<Entry> entries;
    protected static Random rng = new Random();

    public MainTimingExperiment() {
        super(problemSizeDescription, problemSizeMin, problemSizeCount, problemSizeStep, experimentIterationCount);
    }

    public static void main(String[] args) {

        MainTimingExperiment timingExperiment = new MainTimingExperiment();
        timingExperiment.printResults();

    }

    @Override
    protected void setupExperiment(int problemSize) {
        glossary = new Glossary();
        words = new ArrayList<>();
        entries = new ArrayList<>();

        ArrayList<String> validPartsOfSpeech = new ArrayList<>(List.of("noun", "verb", "adj", "adv", "pron", "prep", "conj", "interj"));

        for (int i = 0; i < problemSize; i++) {

            String word = String.valueOf(rng.nextInt(0, problemSize));
            String partOfSpeech = validPartsOfSpeech.get(rng.nextInt(validPartsOfSpeech.size()));
            StringBuilder definition = new StringBuilder();

            for (int j = 0; j < rng.nextInt(2, 6); j++) {
                definition.append(" ").append(word);
            }

            words.add(word);
            entries.add(new Entry(partOfSpeech, definition.toString()));

            glossary.option9(words.get(i), entries.get(i).getPartOfSpeech(), entries.get(i).getDefinition());
        }

//        words.sort(Comparator.naturalOrder());
    }

    @Override
    protected void runComputation() {

        glossary.option1();

//        for (int i = 0; i < words.size(); i++) {

//            int firstIndex = rng.nextInt(words.size() / 2);
//            int secondIndex = rng.nextInt(words.size() / 2, words.size());
//
//            glossary.option2(words.get(firstIndex), words.get(secondIndex));

//            glossary.option8(words.get(i), 0);


//            glossary.option3(words.get(i), false);
//            glossary.option9(words.get(i), entries.get(i).getPartOfSpeech(), entries.get(i).getDefinition());
//        }

    }

}
