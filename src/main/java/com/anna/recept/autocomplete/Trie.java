package com.anna.recept.autocomplete;

/**
 * Interface for Trie structure
 * @author Hanna_Sira
 */
public interface Trie {

    /**
     * Add in Trie tuple of term and its weigh as Tuple object
     * @param tuple
     */
    void add(Tuple tuple);

    /**
     * Chechs if Trie contains the word
     * @param word given word
     * @return true if contains
     */
    boolean contains(String word);

    /**
     * Deletes word from Trie
     * @param word word for removing
     * @return true if word was removed
     */
    boolean delete(String word);

    /**
     * Chooses all words from Trie moving in width
     * @return all words in Trie
     */
    Iterable<String> words();

    /**
     * Chooses all words from Trie moving in width which starts with prefix pref
     * @param pref prefix
     * @return all words in Trie starts with prefix pref
     */
    Iterable<String> wordsWithPrefix(String pref);

    /**
     * Counts word's amount in Trie
     * @return Trie size
     */
    int size();

}