// AUTO-COMPLETE TREE
// Patrick Lindsay
// 5/30/22
// File: WordTree.java

// This class is the tree structure that stores the dictionary words.
// There is a method to add words to the tree, and a method to search
// for words that match a given starting sequence.

package search;

import java.util.ArrayList;

/**
 * This class constructs a tree data-structure that stores words.
 * @author Patrick Lindsay
 * @version 1.0
 */
public class    WordTree {
    // CONSTANTS
    private static final int ALPHABET_SIZE = 26;
    private static final int ASCII_START = 97;

    // FIELDS
    private final Node root;

    /**
     * Constructs a tree of letter nodes where each tree path represents a word
     * stored in the tree.
     */
    public WordTree() {
        root = new Node();
    }

    //   =====   METHODS   =====   //

    /**
     * Method to add a word to the tree.
     * @param word to add to the tree
     */
    public void addWord(String word) {
        // Add word to tree
        addLetters(root, word);
    }

    // Method to recursively add a word to the tree
    // Adds first letter in the string as a child node to the current node
    private Node addLetters(Node current, String word) {
        // Store current letter to be added
        char letter = word.charAt(0);

        // BASE CASE (last letter to add)
        if (word.length() == 1) {
            // Check if child node exists
            if (current.hasChild(letter)) {
                Node child = current.getChild(letter);
                if (child != null) {
                    child.setWordEnd();
                }
            }
            else {
                current.addChild(new Node(letter, true));
            }
            return current;
        }

        // Check if current has next letter as a child
        if (current.hasChild(letter)) {
            addLetters(current.getChild(letter), word.substring(1));
        }
        else {
            // Create new Node for letter
            Node newNode = addLetters(new Node(letter), word.substring(1));
            current.addChild(newNode);
        }
        // Return current node to be added as a child
        return current;
    }


    /**
     * Method to get all words starting with given string
     * @param startingSequence to search for in the tree
     * @return Array of words matching given pattern
     */
    public String[] getWords(String startingSequence) {
        // Validate input is not blank/empty
        if (startingSequence.isBlank()) {
            return new String[0];
        }
        else {
            ArrayList<String> words = getMatches(root, startingSequence, "");
            return words.toArray(String[]::new);
        }
    }

    // Method to recursively traverse tree
    private ArrayList<String> getMatches(Node current, String start, String sequence) {

        // Traverse tree if pattern is not ""
        if (!start.isEmpty()) {
            // If next letter is a child of current traverse it, otherwise return empty list
            Node childNode = current.getChild(start.charAt(0));
            if (childNode != null) {
                return getMatches(childNode, start.substring(1), sequence + current.letter);
            }
            return new ArrayList<>();
        }

        // If current is a word end, add word to list
        ArrayList<String> words = new ArrayList<>();
        if (current.isWordEnd) {
            // Add word to list
            words.add(sequence + current.letter);
        }

        // If current node is NOT a leaf node, traverse child nodes
        if (!current.isLeaf) {
            // For each child node
            for (Node child : current.children) {
                if (child != null) {
                    words.addAll(getMatches(child, start, sequence + current.letter));
                }
            }
        }

        // return list of words
        return words;
    }

    @Override
    public String toString() {
        return "WordTree";
    }


    // Private node class to store and link letters to form words
    private static class Node {
        // FIELDS
        private char letter;
        private final Node[] children;
        private boolean isWordEnd;
        private boolean isLeaf;

        // CONSTRUCTORS

        private Node() {
            this.children = new Node[ALPHABET_SIZE];
            this.isWordEnd = false;
            this.isLeaf = true;
        }

        private Node(char letter) {
            this();
            this.letter = letter;
        }

        private Node(char letter, boolean wordEnd) {
            this(letter);
            this.isWordEnd = wordEnd;
        }


        // METHODS //

        // Method to get a child node (Returns null if child doesn't Exist)
        private Node getChild(char letter) {
            int index = (int) letter - ASCII_START;
            if (index < 0 || index >= ALPHABET_SIZE) {
                return null;
            }
            return children[index];
        }

        // Add given node to the array of child nodes
        private void addChild(Node child) {
            int index = (int) child.letter - ASCII_START;
            children[index] = child;
            isLeaf = false;
        }

        // Method to check if child Node exists
        private boolean hasChild(char letter) {
            int index = (int) letter - ASCII_START;
            return children[index] != null;
        }

        // Method to set a node to be the last in a word
        private void setWordEnd() {
            isWordEnd = true;
        }

        @Override
        public String toString() {
            return "" + letter;
        }
    }
}
