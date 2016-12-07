package com.anna.recept.repository.impl;

import com.anna.recept.persist.Detail;
import com.anna.recept.repository.IDetailRepository;
import com.anna.recept.repository.RepositoryTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class DetailRepositoryTest extends RepositoryTest {

    @Autowired
    private IDetailRepository sut;

    private int receptId;
    private int detailId;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        int departId = departRepository.save(constructDepart());
        receptId = receptRepository.save(constructRecept(departId));
        detailId = sut.save(constructDetail(receptId));
    }

    @Test
    public void shouldfindByRecept() {
        List<Detail> detailList = sut.findByRecept(receptId);
        assertNotNull(detailList);
        assertThat(detailList.size(), is(1));
        assertThat(detailList.get(0).getReceptId(), is(receptId));
    }
}