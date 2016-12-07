package com.anna.recept.autocomplete;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RVTrieTest {

    private static final String WORD_1 = "vasilisa";
    private static final String WORD_2 = "vasya";
    private static final String WORD_3 = "vasilisssa";
    private static final String PREFIX = "vasi";
    private static final int WORD_COUNT = 2;
    RVTrie trie;

    /**
     * Test of add and contains method, of class RVTrie.
     */
    @Test
    public void testAdd() {
        Tuple tuple = new Tuple(WORD_1);
        trie = new RVTrie();
        trie.add(tuple);
        assertEquals(true, trie.contains(WORD_1));
    }

    /**
     * Test of delete method, of class MyTrie.
     */
    @Test
    public void testDeleteSuccesfully() {
        Tuple tuple = new Tuple(WORD_1);
        trie = new RVTrie();
        trie.add(tuple);
        assertEquals(true, trie.delete(WORD_1));
    }

    /**
     * Test of delete method, of class MyTrie.
     */
    @Test
    public void testDeleteNonSuccesfully() {
        trie = new RVTrie();
        assertEquals(false, trie.delete(WORD_1));
    }

    /**
     * Test of words method, of class MyTrie.
     */
    @Test
    public void testWords() {
        trie = new RVTrie();
        List<String> expectedResult = new ArrayList<String>();
        expectedResult.add(WORD_1);
        expectedResult.add(WORD_2);
        expectedResult.add(WORD_3);

        trie.add(new Tuple(WORD_1));
        trie.add(new Tuple(WORD_2));
        trie.add(new Tuple(WORD_3));

        List<String> actualResult = new ArrayList<String>();

        Iterator<String> iterator = trie.words().iterator();
        while(iterator.hasNext()) {
            actualResult.add(iterator.next());
        }
        assertEquals(true, CollectionUtils.isEqualCollection(expectedResult, actualResult));
    }

    /**
     * Test of wordsWithPrefix method, of class MyTrie.
     */
    @Test
    public void testWordsWithPrefix() {
        trie = new RVTrie();
        List<String> expectedResult = new ArrayList<String>();
        expectedResult.add(WORD_1);
        expectedResult.add(WORD_3);

        trie.add(new Tuple(WORD_1));
        trie.add(new Tuple(WORD_2));
        trie.add(new Tuple(WORD_3));

        List<String> actualResult = new ArrayList<String>();

        Iterator<String> iterator = trie.wordsWithPrefix(PREFIX).iterator();
        while(iterator.hasNext()) {
            actualResult.add(iterator.next());
        }

        assertEquals(true, CollectionUtils.isEqualCollection(expectedResult, actualResult));
    }

    /**
     * Test of size method, of class MyTrie.
     */
    @Test
    public void testSize() {
        trie = new RVTrie();

        trie.add(new Tuple(WORD_1));
        trie.add(new Tuple(WORD_2));
        trie.add(new Tuple(WORD_3));

        assertEquals(3, trie.size());
    }
}