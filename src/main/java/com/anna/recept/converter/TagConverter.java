package com.anna.recept.converter;

import com.anna.recept.dto.TagDto;
import com.anna.recept.persist.Tag;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagConverter {

    public static TagDto toTagDto(Tag entity){
        TagDto tagDto = new TagDto();
        tagDto.setId(entity.getId());
        tagDto.setName(entity.getName());
        return tagDto;
    }
}
