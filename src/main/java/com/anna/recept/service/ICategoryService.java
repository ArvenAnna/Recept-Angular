package com.anna.recept.service;

import com.anna.recept.dto.TagDto;

import java.util.List;

public interface ICategoryService {
    void deleteReceptsCategory(Integer receptId);

    List<TagDto> findTagsByRecept(Integer receptId);

    Integer saveCategory(Integer receptId, Integer tagId);

    void deleteTagFromRecept(Integer receptId, Integer tagId);
}
