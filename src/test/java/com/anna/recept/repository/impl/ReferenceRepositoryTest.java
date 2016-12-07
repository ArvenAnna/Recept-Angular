package com.anna.recept.repository.impl;

import com.anna.recept.persist.Reference;
import com.anna.recept.repository.IReferenceRepository;
import com.anna.recept.repository.RepositoryTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ReferenceRepositoryTest extends RepositoryTest {

    @Autowired
    private IReferenceRepository sut;

    private int receptId;
    private int receptReferenceId;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        int departId = departRepository.save(constructDepart());
        receptId = receptRepository.save(constructRecept(departId));
        receptReferenceId = receptRepository.save(constructRecept(departId));
        sut.save(constructReference(receptId, receptReferenceId));
    }

    @Test
    public void shouldfindByRecept() {
        List<Reference> referenceList = sut.findByRecept(receptId);
        assertNotNull(referenceList);
        assertThat(referenceList.size(), is(1));
        assertThat(referenceList.get(0).getReceptId(), is(receptId));
        assertThat(referenceList.get(0).getReceptReferenceId(), is(receptReferenceId));
    }
}
