package com.anna.recept.service.impl;

import com.anna.recept.dto.IngridientDto;
import com.anna.recept.exception.ReceptApplicationException;
import com.anna.recept.persist.Ingridient;
import com.anna.recept.repository.IIngridientRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IngridientServiceTest {

    private static final Integer INGRIDIENT_ID = 34;

    @InjectMocks
    private IngridientService sut;

    @Mock
    private IIngridientRepository ingRep;

    private static Integer LIST_SIZE = 1;

    @Before
    public void setUp() {
        when(ingRep.findById(anyInt())).thenReturn(new Ingridient());
        when(ingRep.findAll()).thenReturn(constructIngridientList(LIST_SIZE));
        when(ingRep.save(any())).thenReturn(INGRIDIENT_ID);
    }

    @Test
    public void shouldFindIngridient() {
        int ingId = 5;
        IngridientDto ingridient = sut.findIngridient(ingId);

        assertNotNull(ingridient);
        verify(ingRep).findById(ingId);
    }

    @Test
    public void shouldShowIngridients() {
        List<IngridientDto> ingridientList = sut.showAllIngridients();

        assertNotNull(ingridientList);
        assertThat(ingridientList.size(), is(LIST_SIZE));
        verify(ingRep).findAll();
    }

    @Test
    public void shouldDeleteIngridient() {
        sut.deleteIngridient(INGRIDIENT_ID);

        verify(ingRep).findById(INGRIDIENT_ID);
        verify(ingRep).delete(any());
    }

    @Test
    public void shouldSaveIngridient() {
        Integer ingId = sut.saveIngridient(new IngridientDto());

        verify(ingRep).save(any());
        assertThat(ingId, is(INGRIDIENT_ID));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotSaveIngridient() {
        IngridientDto ingridientDto = new IngridientDto();
        ingridientDto.setId(INGRIDIENT_ID);
        Integer ingId = sut.saveIngridient(ingridientDto);
    }

    @Test
    public void shouldSaveUniqueIngridient() {
        when(ingRep.findByName(any())).thenReturn(null);
        Integer ingId = sut.saveUniqueIngridient(new IngridientDto());

        verify(ingRep).save(any());
        assertThat(ingId, is(INGRIDIENT_ID));

    }

    @Test(expected = ReceptApplicationException.class)
    public void shouldNotSaveNotUniqueIngridient() {
        when(ingRep.findByName(any())).thenReturn(new Ingridient());
        sut.saveUniqueIngridient(new IngridientDto());
    }

    private List<Ingridient> constructIngridientList(int number) {
        List<Ingridient> ingList = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            ingList.add(new Ingridient());
        }
        return ingList;
    }
}