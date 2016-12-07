package com.anna.recept.controller;

import com.anna.recept.dto.IngridientDto;
import com.anna.recept.dto.ReferenceDto;
import com.anna.recept.service.IAutoCompleteService;
import com.anna.recept.service.IIngridientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IngridientController {
    @Autowired
    private IIngridientService ingridientService;

    @RequestMapping(value = {"/ing_list.req"}, method = RequestMethod.GET,
            headers = "Accept=application/json")
    @ResponseBody
    public List<IngridientDto> showIngridients() {
        return ingridientService.showAllIngridients();
    }


    @RequestMapping(value = {"/ingridient.req"}, method = RequestMethod.POST,
            headers = "Accept=application/json")
    @ResponseBody
    public Integer saveIngridient(@RequestBody IngridientDto ingridient) {
        return ingridientService.saveUniqueIngridient(ingridient);
    }

    @RequestMapping(value = {"/ingridient.req"}, method = RequestMethod.DELETE,
            headers = "Accept=application/json")
    @ResponseBody
    public void deleteIngridient(@RequestParam("ingId") Integer ingId) {
        ingridientService.deleteIngridient(ingId);
    }

}
