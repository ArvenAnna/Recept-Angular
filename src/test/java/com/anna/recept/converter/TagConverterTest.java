package com.anna.recept.converter;

import com.anna.recept.dto.TagDto;
import com.anna.recept.persist.Tag;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;

public class TagConverterTest {

    private static final Integer TAG_ID = 3;
    private static final String TAG_NAME = "tag";

    @Test
    public void shouldConvertEntityToTagDto() {
        Tag entity = new Tag();
        entity.setId(TAG_ID);
        entity.setName(TAG_NAME);

        TagDto tag = TagConverter.toTagDto(entity);

        assertNotNull(tag);
        assertThat(tag.getId(), is(TAG_ID));
        assertThat(tag.getName(), is(TAG_NAME));
    }
}