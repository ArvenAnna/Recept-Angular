package com.anna.recept.converter;

import com.anna.recept.dto.IngridientDto;
import com.anna.recept.dto.ProportionDto;
import com.anna.recept.persist.Proportion;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;

public class ProportionConverterTest {

    private static final Integer PROPORTION_ID = 13;
    private static final Integer RECEPT_ID = 11;
    private static final String NORMA = "norma";

    @Test
    public void shouldConvertEntityToProportionDto() {
        Proportion entity = new Proportion();
        entity.setId(PROPORTION_ID);
        entity.setNorma(NORMA);

        ProportionDto proportion = ProportionConverter.toProportionDto(entity, new IngridientDto());

        assertNotNull(proportion);
        assertThat(proportion.getId(), is(PROPORTION_ID));
        assertThat(proportion.getNorma(), is(NORMA));
        assertNotNull(proportion.getIngridient());
    }

    @Test
    public void shouldConvertProportionDtoToEntity() {
        ProportionDto proportionDto = new ProportionDto();
        proportionDto.setId(PROPORTION_ID);
        proportionDto.setNorma(NORMA);
        int ingId = 80;
        IngridientDto ingridient = new IngridientDto();
        ingridient.setId(ingId);
        proportionDto.setIngridient(ingridient);

        Proportion proportion = ProportionConverter.toProportionEntity(proportionDto, RECEPT_ID);

        assertNotNull(proportion);
        assertThat(proportion.getId(), is(PROPORTION_ID));
        assertThat(proportion.getNorma(), is(NORMA));
        assertThat(proportion.getReceptId(), is(RECEPT_ID));
        assertThat(proportion.getIngridientId(), is(ingId));
    }

}