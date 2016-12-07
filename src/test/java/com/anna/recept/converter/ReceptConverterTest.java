package com.anna.recept.converter;

import com.anna.recept.dto.DepartDto;
import com.anna.recept.dto.ReceptDto;
import com.anna.recept.persist.Recept;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ReceptConverterTest {

    private static final Integer RECEPT_ID = 6;
    private static final String RECEPT_NAME = "name";
    private static final String TEXT = "text";
    private static final Integer DEPART_ID = 3;
    private static final String IMG_PATH = "some/path";

    @Test
    public void shouldConvertEntityToReceptDto() {
        ReceptDto receptDto = new ReceptDto();
        receptDto.setId(RECEPT_ID);
        receptDto.setName(RECEPT_NAME);
        receptDto.setText(TEXT);
        DepartDto depart =  new DepartDto();
        depart.setId(DEPART_ID);
        receptDto.setDepartId(depart);
        receptDto.setImgPath(IMG_PATH);

        Recept entity = ReceptConverter.toReceptEntity(receptDto);

        assertNotNull(entity);
        assertThat(entity.getId(), is(RECEPT_ID));
        assertThat(entity.getName(), is(RECEPT_NAME));
        assertThat(entity.getText(), is(TEXT));
        assertThat(entity.getDepartId(), is(DEPART_ID));
        assertThat(entity.getImgPath(), is(IMG_PATH));
    }

    @Test
    public void shouldConvertReceptDtoToEntity() {
        Recept entity = new Recept();
        entity.setId(RECEPT_ID);
        entity.setName(RECEPT_NAME);
        entity.setText(TEXT);
        entity.setImgPath(IMG_PATH);

        ReceptDto recept = ReceptConverter.toReceptDto(entity);

        assertNotNull(recept);
        assertThat(recept.getId(), is(RECEPT_ID));
        assertThat(recept.getName(), is(RECEPT_NAME));
        assertThat(recept.getText(), is(TEXT));
        assertThat(recept.getImgPath(), is(IMG_PATH));
    }

}