package com.anna.recept.converter;

import com.anna.recept.dto.DetailDto;
import com.anna.recept.persist.Detail;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;

public class DetailConverterTest {

    private static final Integer DETAIL_ID = 22;
    private static final String DESCRIPTION = "description";
    private static final Integer RECEPT_ID = 25;
    private static final String FILE_PATH = "some/path";

    @Test
    public void shouldConvertEntityToDetailDto() {
        Detail entity = new Detail();
        entity.setId(DETAIL_ID);
        entity.setDescription(DESCRIPTION);
        entity.setReceptId(RECEPT_ID);
        entity.setFilePath(FILE_PATH);

        DetailDto detail = DetailConverter.toDetailDto(entity);

        assertNotNull(detail);
        assertThat(detail.getId(), is(DETAIL_ID));
        assertThat(detail.getDescription(), is(DESCRIPTION));
        assertThat(detail.getReceptId(), is(RECEPT_ID));
        assertThat(detail.getFilePath(), is(FILE_PATH));
    }

    @Test
    public void shouldConvertDetailDtoToEntity() {
        DetailDto detailDto = new DetailDto();
        detailDto.setId(DETAIL_ID);
        detailDto.setDescription(DESCRIPTION);
        detailDto.setReceptId(RECEPT_ID);
        detailDto.setFilePath(FILE_PATH);

        Detail detail = DetailConverter.toDetailEntity(detailDto);

        assertNotNull(detail);
        assertThat(detail.getId(), is(DETAIL_ID));
        assertThat(detail.getDescription(), is(DESCRIPTION));
        assertThat(detail.getReceptId(), is(RECEPT_ID));
        assertThat(detail.getFilePath(), is(FILE_PATH));
    }

}