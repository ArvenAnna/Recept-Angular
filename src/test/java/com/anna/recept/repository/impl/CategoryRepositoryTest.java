package com.anna.recept.repository.impl;

import com.anna.recept.persist.Category;
import com.anna.recept.repository.ICategoryRepository;
import com.anna.recept.repository.RepositoryTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class CategoryRepositoryTest extends RepositoryTest {

    @Autowired
    private ICategoryRepository sut;

    private int receptId;
    private int tagId;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        int departId = departRepository.save(constructDepart());
        receptId = receptRepository.save(constructRecept(departId));
        tagId = tagRepository.save(constructTag());
        categoryRepository.save(constructCategory(receptId, tagId));
    }

    @Test
    public void shouldfindByRecept() {
        List<Category> categoryList = sut.findByRecept(receptId);

        assertNotNull(categoryList);
        assertThat(categoryList.size(), is(1));
        assertThat(categoryList.get(0).getReceptId(), is(receptId));
        assertThat(categoryList.get(0).getTagId(), is(tagId));
    }

}