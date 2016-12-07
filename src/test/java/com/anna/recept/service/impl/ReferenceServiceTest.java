package com.anna.recept.service.impl;

import com.anna.recept.dto.ReceptDto;
import com.anna.recept.dto.ReferenceDto;
import com.anna.recept.persist.Reference;
import com.anna.recept.repository.IReferenceRepository;
import com.anna.recept.service.IReceptService;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReferenceServiceTest {

    @InjectMocks
    private ReferenceService sut;

    @Mock
    private IReferenceRepository refRep;
    @Mock
    private IReceptService receptServ;

    private static Integer REFERENCE_LIST_SIZE = 1;
    private static Integer RECEPT_ID = 23;
    private static Integer RECEPT_REFERENCE_ID = 13;
    private static Integer REFERENCE_ID = 55;

    @Before
    public void setUp() {
        when(refRep.findByRecept(anyInt())).thenReturn(createReferenceList(REFERENCE_LIST_SIZE));
        when(receptServ.getRecept(anyInt())).thenReturn(new ReceptDto());
        when(refRep.save(any())).thenReturn(REFERENCE_ID);
    }

    @Test
    public void shouldFindReceptsReferences() {
        List<ReferenceDto> references = sut.findReceptsReferences(RECEPT_ID);

        assertNotNull(references);
        assertThat(references.size(), is(REFERENCE_LIST_SIZE));
        assertThat(references.get(0).getReceptReferenceId(), is(RECEPT_REFERENCE_ID));

        verify(refRep).findByRecept(RECEPT_ID);
        verify(receptServ).getRecept(RECEPT_REFERENCE_ID);
    }

    @Test
    public void shouldDeleteReferences() {
        sut.deleteReceptReferences(RECEPT_ID);

        verify(refRep).findByRecept(RECEPT_ID);
        verify(refRep, times(REFERENCE_LIST_SIZE)).delete(any());
    }

    @Test
    public void shouldSaveRef() {
        Integer refId = sut.saveReference(constructReferenceDto(), RECEPT_ID);

        verify(refRep).save(any());
        assertThat(refId, is(REFERENCE_ID));
    }

    @Test
    public void shouldDeleteReference() {
        sut.deleteReference(REFERENCE_ID);

        verify(refRep).findById(REFERENCE_ID);
        verify(refRep).delete(any());
    }

    private ReferenceDto constructReferenceDto() {
        ReferenceDto reference = new ReferenceDto();
        reference.setReceptReferenceId(RECEPT_REFERENCE_ID);
        return reference;
    }

    private List<Reference> createReferenceList(int referenceNumber) {
        List<Reference> referenceList = new ArrayList<>();
        for (int i = 0; i < referenceNumber; i++) {
            referenceList.add(createReference());
        }
        return referenceList;
    }

    private Reference createReference() {
        Reference reference = new Reference();
        reference.setReceptReferenceId(RECEPT_REFERENCE_ID);
        return reference;
    }

}