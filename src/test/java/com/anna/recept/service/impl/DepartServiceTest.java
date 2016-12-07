package com.anna.recept.service.impl;

import com.anna.recept.converter.DepartConverter;
import com.anna.recept.dto.DepartDto;
import com.anna.recept.persist.Dapart;
import com.anna.recept.repository.IDepartRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DepartServiceTest {

    @Mock
    IDepartRepository repository;

    @InjectMocks
    DepartService sut;

    private static Integer DEPART_LIST_SIZE = 1;

    @Test
    public void shouldFindDepartmentByReceptId() {
        int receptId = 2;
        Dapart expected = new Dapart();
        when(repository.findByRecept(anyInt())).thenReturn(expected);

        DepartDto actual = sut.findDepartmentByReceptId(receptId);

        assertNotNull(actual);
    }

    @Test
    public void shouldFindAllDepartments() {
        List<Dapart> departList = createDapartList(DEPART_LIST_SIZE);
        List<DepartDto> expectedList = convertDepartListToDto(departList);
        when(repository.findAll()).thenReturn(departList);

        List<DepartDto> actualList = sut.findAllDepartments();

        assertThat(actualList, is(expectedList));
    }

    private Dapart createDepart() {
        return new Dapart();
    }

    private List<Dapart> createDapartList(int departNumber) {
        List<Dapart> departList = new ArrayList<>();
        for (int i = 0; i < departNumber; i++) {
            departList.add(createDepart());
        }
        return departList;
    }

    private List<DepartDto> convertDepartListToDto(List<Dapart> departList) {
        return departList.stream()
                .map(DepartConverter::toDepartDto)
                .collect(Collectors.toList());
    }
}
