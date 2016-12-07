package com.anna.recept.converter;

import com.anna.recept.dto.IngridientDto;
import com.anna.recept.persist.Ingridient;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;

public class IngridientConverterTest {

    private static final Integer ING_ID = 12;
    private static final String ING_NAME = "name";

    @Test
    public void shouldConvertEntityToIngridientDto() {
        Ingridient entity = new Ingridient();
        entity.setId(ING_ID);
        entity.setName(ING_NAME);

        IngridientDto ingridient = IngridientConverter.toIngridientDto(entity);

        assertNotNull(ingridient);
        assertThat(ingridient.getId(), is(ING_ID));
        assertThat(ingridient.getName(), is(ING_NAME));
    }

    @Test
    public void shouldConvertIngridientDtoToEntity() {
        IngridientDto ingridientDto = new IngridientDto();
        ingridientDto.setId(ING_ID);
        ingridientDto.setName(ING_NAME);

        Ingridient ingridient = IngridientConverter.toIngridientEntity(ingridientDto);

        assertNotNull(ingridient);
        assertThat(ingridient.getId(), is(ING_ID));
        assertThat(ingridient.getName(), is(ING_NAME));
    }
}