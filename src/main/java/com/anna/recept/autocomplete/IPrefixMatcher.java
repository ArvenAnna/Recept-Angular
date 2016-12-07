package com.anna.recept.autocomplete;

import java.util.List;

public interface IPrefixMatcher {
    /**
     * Формирует in-memory словарь слов.
     * В словарь должны добавляться слова длиннее 2х символов.
     * Предполагается что знаки пунктуации отсутствуют.
     * @param strings слово, строка, массив слов/строк.
     * Если приходит строка, то она разбивается на слова по пробелам.
     * @return
     */
    int add(List<String> strings);

    /**
     * Формирует in-memory словарь слов.
     * В словарь должны добавляться слова длиннее 2х символов.
     * Предполагается что знаки пунктуации отсутствуют.
     *
     * @param string слово или строка.
     *                Если приходит строка, то она разбивается на слова по пробелам.
     * @return
     */
    int add(String string);

    /**
     * Checks if the word contained in vocabulary
     * @param word given word
     * @return true if contains
     */
    boolean contains(String word);

    /**
     * Detetes word from vokebulary
     * @param word given word
     * @return
     */
    boolean delete(String word);

    /**
     * Counts vocabulary size
     * @return vocabulary size
     */
    int size();
    /**
     * Предоставляет набор слов начинающихся с префикса k разных длин
     * @param pref префикс
     * @param k набор длин слов
     * @return если введенный pref длиннее или равен 2м символам,
     * то возвращает набор слов k разных длин начиная с минимальной,
     * и начинающихся с данного префикса pref.
     */
    Iterable<String> wordsWithPrefix(String pref, int k);
    /**
     * Предоставляет набор слов начинающихся с префикса длиной pref+2 смвола
     * @param pref
     * @return если введенный pref длиннее или равен 2м символам,
     * то возвращает набор слов k=3 разных длин начиная с минимальной,
     * и начинающихся с данного префикса pref.
     */
    Iterable<String> wordsWithPrefix(String pref);
}
