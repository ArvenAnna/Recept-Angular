package com.anna.recept.service;

import java.util.List;

public interface IAutoCompleteService {
    List<String> showIngridients(String prefix);

    void addIngridient(String ingridient);

    void addReference(String recept);

    void removeReference(String recept);

    void removeIngridient(String ingridient);

    List<String> showReferences(String word);
}
