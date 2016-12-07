package com.anna.recept.converter;

import com.anna.recept.dto.DepartDto;
import com.anna.recept.persist.Dapart;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;

public class DepartConverterTest {

    private static final Integer DEPART_ID = 23;
    private static final String DEPART_NAME = "depart";

    @Test
    public void shouldConvertEntityToDepartDto() {
        Dapart entity = new Dapart();
        entity.setId(DEPART_ID);
        entity.setName(DEPART_NAME);

        DepartDto depart = DepartConverter.toDepartDto(entity);

        assertNotNull(depart);
        assertThat(depart.getId(), is(DEPART_ID));
        assertThat(depart.getName(), is(DEPART_NAME));
    }
}