package com.anna.recept.controller;

import com.anna.recept.service.IAutoCompleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AutocompleteController {

    @Autowired
    private IAutoCompleteService autoCompleteService;

    @RequestMapping(value = {"/ing_suggest.req"}, method = RequestMethod.GET,
            headers = "Accept=application/json")
    @ResponseBody
    public List<String> suggestIngridients(@RequestParam("word") String word) {
        return autoCompleteService.showIngridients(word);
    }

    @RequestMapping(value = {"/ref_suggest.req"}, method = RequestMethod.GET,
            headers = "Accept=application/json")
    @ResponseBody
    public List<String> suggestReferences(@RequestParam("word") String word) {
        return autoCompleteService.showReferences(word);
    }
}
