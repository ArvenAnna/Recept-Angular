package com.anna.recept.service.impl;

import com.anna.recept.dto.IngridientDto;
import com.anna.recept.dto.ProportionDto;
import com.anna.recept.exception.ReceptApplicationException;
import com.anna.recept.persist.Ingridient;
import com.anna.recept.persist.Proportion;
import com.anna.recept.repository.IIngridientRepository;
import com.anna.recept.repository.IProportionRepository;
import com.anna.recept.service.IIngridientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProportionServiceTest {

    @InjectMocks
    private ProportionService sut;

    @Mock
    private IProportionRepository propRep;
    @Mock
    private IIngridientService ingServ;

    private static Integer PROPORTION_LIST_SIZE = 1;
    private static Integer INGRIDIENT_ID = 34;
    private static Integer RECEPT_ID = 45;
    private static Integer PROPORTION_ID = 5;

    @Before
    public void setUp() {
        when(propRep.findByRecept(anyInt())).thenReturn(createProportionList(PROPORTION_LIST_SIZE));
        when(ingServ.findIngridient(anyInt())).thenReturn(new IngridientDto());
        when(propRep.save(any())).thenReturn(PROPORTION_ID);
    }

    @Test
    public void shouldFindReceptsProportions() {
        List<ProportionDto> proportions = sut.findReceptsProportions(RECEPT_ID);

        assertNotNull(proportions);
        assertThat(proportions.size(), is(PROPORTION_LIST_SIZE));
        assertNotNull(proportions.get(0).getIngridient());
        verify(propRep).findByRecept(RECEPT_ID);
        verify(ingServ).findIngridient(INGRIDIENT_ID);
    }

    @Test
    public void shouldDeleteProportions() {
        sut.deleteProportions(RECEPT_ID);

        verify(propRep).findByRecept(RECEPT_ID);
        verify(propRep, times(PROPORTION_LIST_SIZE)).delete(any());
    }

    @Test
    public void shouldSaveProportion() {
        Integer propId = sut.saveProportion(constructProportionDto(), RECEPT_ID);

        verify(propRep).save(any());
        assertThat(propId, is(PROPORTION_ID));
    }

    @Test(expected = ReceptApplicationException.class)
    public void shouldNotSaveProportion() {
        Integer propId = sut.saveProportion(new ProportionDto(), RECEPT_ID);

        verify(propRep).save(any());
        assertThat(propId, is(PROPORTION_ID));
    }

    @Test
    public void shouldUpdateProportion() {
        Integer propId = sut.saveProportion(constructProportionDto(), RECEPT_ID);

        verify(propRep).save(any());
        assertThat(propId, is(PROPORTION_ID));
    }

    @Test
    public void shouldDeleteProportion() {
        sut.deleteProportion(PROPORTION_ID);

        verify(propRep).findById(PROPORTION_ID);
        verify(propRep).delete(any());
    }

    private List<Proportion> createProportionList(int proportionNumber) {
        List<Proportion> proportionList = new ArrayList<>();
        for (int i = 0; i < proportionNumber; i++) {
            proportionList.add(createProportion());
        }
        return proportionList;
    }

    private Proportion createProportion() {
        Proportion proportion = new Proportion();
        proportion.setIngridientId(INGRIDIENT_ID);
        return proportion;
    }

    private ProportionDto constructProportionDto() {
        ProportionDto proportion = new ProportionDto();
        proportion.setNorma("norma");
        IngridientDto ingridient = new IngridientDto();
        ingridient.setId(INGRIDIENT_ID);
        proportion.setIngridient(ingridient);
        return proportion;
    }

}