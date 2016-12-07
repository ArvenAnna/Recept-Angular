package com.anna.recept.repository.impl;

import com.anna.recept.persist.Dapart;
import com.anna.recept.repository.IDepartRepository;
import com.anna.recept.repository.RepositoryTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DepartRepositoryTest extends RepositoryTest {

    @Autowired
    private IDepartRepository sut;

    private int departId;
    private int receptId;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        departId = departRepository.save(constructDepart());
        receptId = receptRepository.save(constructRecept(departId));
    }

    @Test
    public void shouldFindByRecept() {
        Dapart depart  = sut.findByRecept(receptId);

        assertNotNull(depart);
        assertThat(depart.getId(), is(departId));
    }
}