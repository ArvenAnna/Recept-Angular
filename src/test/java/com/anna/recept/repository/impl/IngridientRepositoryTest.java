package com.anna.recept.repository.impl;

import com.anna.recept.persist.Ingridient;
import com.anna.recept.repository.IIngridientRepository;
import com.anna.recept.repository.RepositoryTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class IngridientRepositoryTest extends RepositoryTest {

    @Autowired
    private IIngridientRepository sut;

    private int ingridientId;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        ingridientId = sut.save(constructIngridient());
    }

    @Test
    public void shouldFindByName() {
        Ingridient ingridient = sut.findByName(RepositoryTest.INGRIDIENT_NAME);

        assertNotNull(ingridient);
        assertThat(ingridient.getName(), is(RepositoryTest.INGRIDIENT_NAME));
    }

    @Test
    public void shouldNotFindByNameInEmptyTable() {
        sut.deleteAll();
        Ingridient ingridient = sut.findByName(RepositoryTest.INGRIDIENT_NAME);

        assertNull(ingridient);
    }

}