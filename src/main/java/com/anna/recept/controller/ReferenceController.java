package com.anna.recept.controller;

import com.anna.recept.dto.ReferenceDto;
import com.anna.recept.service.IReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ReferenceController {
    @Autowired
    private IReferenceService referenceService;

    @RequestMapping(value = {"/reference.req"}, method = RequestMethod.POST,
            headers = "Accept=application/json")
    @ResponseBody
    public Integer saveReference(@RequestBody ReferenceDto reference, @RequestParam("receptId") Integer receptId) {
        return referenceService.saveReference(reference, receptId);
    }

    @RequestMapping(value = {"/reference.req"}, method = RequestMethod.DELETE,
            headers = "Accept=application/json")
    @ResponseBody
    public void deleteReference(@RequestParam("refId") Integer refId) {
        referenceService.deleteReference(refId);
    }

    @RequestMapping(value = {"/references.req"}, method = RequestMethod.GET,
            headers = "Accept=application/json")
    @ResponseBody
    public List<ReferenceDto> fetchReferences(@RequestParam("receptId") Integer receptId) {
        return referenceService.findReceptsReferences(receptId);
    }

}
