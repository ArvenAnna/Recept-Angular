package com.anna.recept.service.impl;

import com.anna.recept.converter.TagConverter;
import com.anna.recept.dto.TagDto;
import com.anna.recept.repository.ITagRepository;
import com.anna.recept.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService implements ITagService {

    @Autowired
    private ITagRepository tagRep;

    @Override
    public List<TagDto> findTags() {
        return tagRep.findAll().stream()
                .map(tag -> TagConverter.toTagDto(tag))
                .collect(Collectors.toList());
    }

    @Override
    public TagDto findTag(Integer tagId) {
        return TagConverter.toTagDto(tagRep.findById(tagId));
    }
}
