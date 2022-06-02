// AUTO-COMPLETE TREE
// Patrick Lindsay
// 5/30/22
// File: DictionarySearch.java

// This class loads a dictionary of words and definitions.
// Interfaces client with word tree to implement auto-complete feature.

package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Stores a dictionary that provides definitions given a word
 * or partial matching for words in the dictionary.
 *
 * @author Patrick Lindsay
 * @version 1.0
 */
public class DictionarySearch implements IDictionary
{
    // CONSTANTS
    private static final File DICTIONARY = new File("./files/dictionary80000.txt");

    // FIELDS
    private final WordTree tree;
    private final HashMap<String, String> definitions;

    /**
     * Creates a new search object with a dictionary loaded and
     * ready for searching.
     */
    public DictionarySearch()
    {
        // Initialize Fields
        tree = new WordTree();
        definitions = new HashMap<>();

        // Extract words and definitions from dictionary
        try {
            Scanner dictionaryScanner = new Scanner(DICTIONARY);

            while (dictionaryScanner.hasNextLine()) {
                // Store word and definition (FORMAT: "word: definition...")
                String nextLine = dictionaryScanner.nextLine();
                int separatorIndex = nextLine.indexOf(':');

                // Extract word and store in tree (Before ":")
                String nextWord = nextLine.substring(0, separatorIndex).trim();
                tree.addWord(nextWord);

                // Extract Definition and store in map (After ": ")
                String nextDefinition = nextLine.substring(separatorIndex + 2).trim();
                definitions.put(nextWord, nextDefinition);
            }
        } catch (FileNotFoundException e) {
            System.out.println(""+e);
        }
    }

    @Override
    public String getDefinition(String word)
    {
        return definitions.get(word);
    }

    @Override
    public String[] getPartialMatches(String search)
    {
        return tree.getWords(search.trim());
    }

    @Override
    public String toString() {
        return "DictionarySearch{}";
    }
}












