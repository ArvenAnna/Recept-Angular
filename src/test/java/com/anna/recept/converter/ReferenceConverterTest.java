package com.anna.recept.converter;

import com.anna.recept.dto.ReferenceDto;
import com.anna.recept.persist.Reference;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;

public class ReferenceConverterTest {
    private static final Integer REF_ID = 4;
    private static final Integer RECEPT_ID = 23;
    private static final Integer RECEPT_REF_ID = 1;
    private static final String RECEPT_REF_NAME = "recept name";

    @Test
    public void shouldConvertEntityToReferenceDto() {
        Reference entity = new Reference();
        entity.setId(REF_ID);
        entity.setReceptReferenceId(RECEPT_REF_ID);

        ReferenceDto reference = ReferenceConverter.toReferenceDto(entity, RECEPT_REF_NAME);

        assertNotNull(reference);
        assertThat(reference.getId(), is(REF_ID));
        assertThat(reference.getReceptReferenceId(), is(RECEPT_REF_ID));
        assertThat(reference.getReceptReferenceName(), is(RECEPT_REF_NAME));
    }
    @Test
    public void shouldConvertReferenceDtoToEntity() {
        ReferenceDto referenceDto = new ReferenceDto();
        referenceDto.setId(REF_ID);
        referenceDto.setReceptReferenceId(RECEPT_REF_ID);

        Reference reference = ReferenceConverter.toReferenceEntity(referenceDto, RECEPT_ID);

        assertNotNull(reference);
        assertThat(reference.getId(), is(REF_ID));
        assertThat(reference.getReceptReferenceId(), is(RECEPT_REF_ID));
        assertThat(reference.getReceptId(), is(RECEPT_ID));
    }
}