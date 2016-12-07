package com.anna.recept.controller;

import com.anna.recept.dto.DetailDto;
import com.anna.recept.dto.TagDto;
import com.anna.recept.service.ICategoryService;
import com.anna.recept.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TagController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ITagService tagService;

    @RequestMapping(value = {"/tags.req"}, method = RequestMethod.GET,
            headers = "Accept=application/json")
    @ResponseBody
    public List<TagDto> fetchTags() {
        return tagService.findTags();
    }


}
