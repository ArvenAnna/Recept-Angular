package com.anna.recept.autocomplete;

import java.util.ArrayList;
import java.util.List;

public class PrefixMatcher implements IPrefixMatcher{

    private Trie trie = new RVTrie();

    @Override
    public int add(List<String> strings) {
        int count = 0;
        for (String arg : strings) {
            count = count + addString(arg);
        }
        return count;
    }

    @Override
    public int add(String string) {
        return addString(string);
    }

    private int addString(String string) {
        int count = 0;
        String mas[] = string.split(" ");
        for (String item : mas) {
            if (item.length() > 2) {
                count++;
                Tuple tuple = new Tuple(item);
                trie.add(tuple);
            }
        }
        return count;
    }

    @Override
    public boolean contains(String word) {
        return trie.contains(word);
    }

    @Override
    public boolean delete(String word) {
        return trie.delete(word);
    }

    @Override
    public int size() {
        return trie.size();
    }

    @Override
    public Iterable<String> wordsWithPrefix(String pref, int k) {
        List<String> words = new ArrayList<>();
        if (pref.length() >= 2) {

            for (String word : trie.wordsWithPrefix(pref)) {
                if (word.length() <= (pref.length() + k - 1)) {
                    words.add(word);
                }
            }
        }
        return words;
    }

    @Override
    public Iterable<String> wordsWithPrefix(String pref) {
        return wordsWithPrefix(pref, 3);
    }
}
