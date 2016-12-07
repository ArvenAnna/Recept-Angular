package com.anna.recept.service.impl;

import com.anna.recept.dto.DepartDto;
import com.anna.recept.dto.ReceptDto;
import com.anna.recept.exception.ReceptApplicationException;
import com.anna.recept.persist.Recept;
import com.anna.recept.repository.IReceptRepository;
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
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReceptServiceTest {

    @InjectMocks
    private ReceptService sut;

    @Mock
    private IReceptRepository receptRep;

    private static int RECEPT_ID = 23;
    private static String RECEPT_NAME = "name";
    private static final Integer ALL_RECEPTS_FETCH_FLAG = -1;

    @Before
    public void setUp() {
        when(receptRep.findByDepart(anyInt())).thenReturn(constructReceptList());
        when(receptRep.findAll()).thenReturn(constructReceptList());
        when(receptRep.findById(anyInt())).thenReturn(new Recept());
        when(receptRep.save(any())).thenReturn(RECEPT_ID);
        when(receptRep.findByTag(anyInt())).thenReturn(constructReceptList());
        when(receptRep.findByName(anyString())).thenReturn(new Recept());
    }

    @Test
    public void shouldGetReceptListByDepartId() {
        int depart_id = 34;
        List<ReceptDto> receptList = sut.showReceptDtos(depart_id);

        assertNotNull(receptList);
        assertThat(receptList.size(), is(2));
        verify(receptRep).findByDepart(depart_id);
    }

    @Test
    public void shouldGetReceptList() {
        int depart_id = ALL_RECEPTS_FETCH_FLAG;
        List<ReceptDto> receptList = sut.showReceptDtos(depart_id);

        assertNotNull(receptList);
        assertThat(receptList.size(), is(2));
        verify(receptRep).findAll();
    }

    @Test
    public void shouldGetReceptByName() {
        ReceptDto recept = sut.getRecept(RECEPT_NAME);

        assertNotNull(recept);

        verify(receptRep).findByName(RECEPT_NAME);
    }

    @Test
    public void shouldGetRecept() {
        int recept_id = 20;
        ReceptDto recept = sut.getRecept(recept_id);

        assertNotNull(recept);
        verify(receptRep).findById(recept_id);
    }

    @Test
    public void shouldDeleteRecept() {
        int recept_id = 20;
        sut.deleteRecept(recept_id);

        verify(receptRep).delete(any());
        verify(receptRep).findById(recept_id);
    }

    @Test
    public void shouldSaveRecept() {
        int receptId = sut.saveRecept(constructReceptDto());

        verify(receptRep).save(any());
        assertThat(receptId, is(RECEPT_ID));
    }

    @Test
    public void shouldSaveReceptWithUniqueName() {
        when(receptRep.findByName(anyString())).thenReturn(null);
        int receptId = sut.saveWithUniqueName(constructReceptDto());

        verify(receptRep).save(any());
        assertThat(receptId, is(RECEPT_ID));
    }

    @Test(expected = ReceptApplicationException.class)
    public void shouldNotSaveReceptWithNotUniqueName() {
        when(receptRep.findByName(any())).thenReturn(new Recept());
        sut.saveWithUniqueName(new ReceptDto());
    }

    @Test
    public void shouldUpdateRecept() {
        ReceptDto recept = constructReceptDto();
        recept.setId(RECEPT_ID);
        int receptId = sut.saveRecept(recept);

        verify(receptRep).update(any());
        assertThat(receptId, is(RECEPT_ID));
    }

    @Test
    public void shouldSaveFilePath() {
        sut.saveFilePath(RECEPT_ID, "file/path");

        verify(receptRep).findById(RECEPT_ID);
        verify(receptRep).update(any());
    }

    private List<Recept> constructReceptList() {
        List<Recept> receptList = new ArrayList<>();
        receptList.add(new Recept());
        receptList.add(new Recept());
        return receptList;
    }

    private ReceptDto constructReceptDto() {
        ReceptDto recept = new ReceptDto();
        recept.setName("recept");
        DepartDto depart = new DepartDto();
        depart.setId(11);
        recept.setDepartId(depart);
        return recept;
    }

    @Test
    public void shouldShowReceptsByTag() {
        int tag_id = 1;
        List<ReceptDto> receptList = sut.showReceptsByTag(tag_id);

        assertNotNull(receptList);
        assertThat(receptList.size(), is(2));
        verify(receptRep).findByTag(tag_id);
    }
}