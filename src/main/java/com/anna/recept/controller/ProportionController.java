package com.anna.recept.controller;

import com.anna.recept.dto.ProportionDto;
import com.anna.recept.service.IProportionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ProportionController {

    @Autowired
    private IProportionService proportionService;

    @RequestMapping(value = {"/proportion.req"}, method = RequestMethod.POST,
            headers = "Accept=application/json")
    @ResponseBody
    public Integer saveProportion(@RequestBody ProportionDto proportion, @RequestParam("receptId") Integer receptId) {
        return proportionService.saveProportion(proportion, receptId);
    }

    @RequestMapping(value = {"/proportion.req"}, method = RequestMethod.DELETE,
            headers = "Accept=application/json")
    @ResponseBody
    public void deleteProportion(@RequestParam("propId") Integer propId) {
        proportionService.deleteProportion(propId);
    }

    @RequestMapping(value = {"/proportions.req"}, method = RequestMethod.GET,
            headers = "Accept=application/json")
    @ResponseBody
    public List<ProportionDto> fetchProportions(@RequestParam("receptId") Integer receptId) {
        return proportionService.findReceptsProportions(receptId);
    }

}
