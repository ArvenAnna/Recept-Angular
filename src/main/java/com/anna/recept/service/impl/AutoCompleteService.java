package com.anna.recept.service.impl;

import com.anna.recept.autocomplete.IPrefixMatcher;
import com.anna.recept.autocomplete.PrefixMatcher;
import com.anna.recept.service.IAutoCompleteService;
import com.anna.recept.service.IIngridientService;
import com.anna.recept.service.IReceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutoCompleteService implements IAutoCompleteService {

    private IPrefixMatcher ingMatcher = new PrefixMatcher();
    private IPrefixMatcher refMatcher = new PrefixMatcher();

    @Autowired
    private IIngridientService ingService;
    @Autowired
    private IReceptService receptService;

    private static final char SPACE_REPLACER = '9';

    @Override
    public List<String> showIngridients(String prefix) {
        List<String> words = new ArrayList<>();
        Iterator<String> iterator = ingMatcher.wordsWithPrefix(prefix.toLowerCase(), 40).iterator();
        while (iterator.hasNext()) {
            words.add(iterator.next().replace(SPACE_REPLACER, ' '));
        }
        return words;
    }

    @Override
    public void addIngridient(String ingridient) {
        ingMatcher.add(ingridient);
    }

    @Override
    public void addReference(String recept) {
        refMatcher.add(recept);
    }

    @Override
    public void removeReference(String recept) {
        refMatcher.delete(recept);
    }

    @Override
    public void removeIngridient(String ingridient) {
        ingMatcher.delete(ingridient);
    }

    @Override
    public List<String> showReferences(String prefix) {
        List<String> words = new ArrayList<>();
        Iterator<String> iterator = refMatcher.wordsWithPrefix(prefix.toLowerCase(), 40).iterator();
        while (iterator.hasNext()) {
            words.add(iterator.next().replace(SPACE_REPLACER, ' '));
        }
        return words;
    }

    @PostConstruct
    private void fillVocabulary() {
        ingMatcher.add(ingService.showAllIngridients().stream()
                .map((ing) -> ing.getName())
                .map((name) -> name.toLowerCase().replace(' ', SPACE_REPLACER))
                .collect(Collectors.toList()));
        refMatcher.add(receptService.showReceptDtos(-1).stream()
                .map((ing) -> ing.getName())
                .map((name) -> name.toLowerCase().replace(' ', SPACE_REPLACER))
                .collect(Collectors.toList()));
    }
}
