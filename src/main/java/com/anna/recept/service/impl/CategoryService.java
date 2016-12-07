package com.anna.recept.service.impl;

import com.anna.recept.dto.TagDto;
import com.anna.recept.persist.Category;
import com.anna.recept.repository.ICategoryRepository;
import com.anna.recept.service.ICategoryService;
import com.anna.recept.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRep;

    @Autowired
    private ITagService tagServ;

    @Override
    public void deleteReceptsCategory(Integer receptId) {
        categoryRep.findByRecept(receptId).stream().forEach(categoryRep::delete);
    }

    @Override
    public List<TagDto> findTagsByRecept(Integer receptId) {
        return categoryRep.findByRecept(receptId).stream()
                .map(category -> tagServ.findTag(category.getTagId()))
                .collect(Collectors.toList());
    }

    @Override
    public Integer saveCategory(Integer receptId, Integer tagId) {
        Category category = new Category();
        category.setReceptId(receptId);
        category.setTagId(tagId);
        return categoryRep.save(category);
    }

    @Override
    public void deleteTagFromRecept(Integer receptId, Integer tagId) {
        categoryRep.findByRecept(receptId).stream()
                .filter(category -> category.getTagId().equals(tagId))
                .forEach(cat -> categoryRep.delete(cat));
    }
}
