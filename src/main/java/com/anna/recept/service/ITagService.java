package com.anna.recept.service;

import com.anna.recept.dto.TagDto;

import java.util.List;

public interface ITagService {
    List<TagDto> findTags();

    TagDto findTag(Integer tagId);
}
