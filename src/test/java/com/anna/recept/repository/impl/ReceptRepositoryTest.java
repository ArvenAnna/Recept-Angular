package com.anna.recept.repository.impl;

import com.anna.recept.persist.Recept;
import com.anna.recept.repository.IReceptRepository;
import com.anna.recept.repository.RepositoryTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ReceptRepositoryTest extends RepositoryTest {

    @Autowired
    private IReceptRepository sut;

    private int departId;
    private int receptId;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        departId = departRepository.save(constructDepart());
        receptId = receptRepository.save(constructRecept(departId));
    }

    @Test
    public void shouldfindByDepart() {
        List<Recept> receptList = sut.findByDepart(departId);
        assertNotNull(receptList);
        assertThat(receptList.size(), is(1));
        assertThat(receptList.get(0).getId(), is(receptId));
    }

    @Test
    public void shouldFindByName() {
        Recept recept = sut.findByName(RepositoryTest.RECEPT_NAME);
        assertNotNull(recept);
        assertThat(recept.getName(), is(RepositoryTest.RECEPT_NAME));
    }

    @Test
    public void shouldNotFindByName() {
        sut.deleteAll();
        Recept recept = sut.findByName(RepositoryTest.RECEPT_NAME);
        assertNull(recept);
    }

    @Test
    public void shouldfindByTag() {
        Integer tagId = tagRepository.save(constructTag());
        categoryRepository.save(constructCategory(receptId, tagId));
        List<Recept> receptList = sut.findByTag(tagId);
        assertNotNull(receptList);
        assertThat(receptList.size(), is(1));
        assertThat(receptList.get(0).getId(), is(receptId));
    }
}
