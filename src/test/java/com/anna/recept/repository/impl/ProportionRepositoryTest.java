package com.anna.recept.repository.impl;

import com.anna.recept.persist.Proportion;
import com.anna.recept.repository.IProportionRepository;
import com.anna.recept.repository.RepositoryTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ProportionRepositoryTest extends RepositoryTest {

    @Autowired
    private IProportionRepository sut;

    private int receptId;
    private int ingridientId;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        int departId = departRepository.save(constructDepart());
        receptId = receptRepository.save(constructRecept(departId));
        ingridientId = ingridientRepository.save(constructIngridient());
        sut.save(constructProportion(receptId, ingridientId));
    }

    @Test
    public void shouldfindByRecept() {
        List<Proportion> proportionList = sut.findByRecept(receptId);
        assertNotNull(proportionList);
        assertThat(proportionList.size(), is(1));
        assertThat(proportionList.get(0).getIngridientId(), is(ingridientId));
    }
}