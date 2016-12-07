package com.anna.recept.service.impl;

import com.anna.recept.dto.TagDto;
import com.anna.recept.persist.Category;
import com.anna.recept.repository.ICategoryRepository;
import com.anna.recept.service.ITagService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService sut;

    @Mock
    private ICategoryRepository categoryRep;

    @Mock
    private ITagService tagServ;

    private static Integer CATEGORY_LIST_SIZE = 1;
    private static Integer RECEPT_ID = 34;
    private static final Integer CATEGORY_ID = 80;
    private static final Integer TAG_ID = 82;

    @Before
    public void setUp() {
        when(categoryRep.findByRecept(anyInt())).thenReturn(createCategoryList(CATEGORY_LIST_SIZE));
        when(tagServ.findTag(anyInt())).thenReturn(new TagDto());
        when(categoryRep.save(any())).thenReturn(CATEGORY_ID);
    }

    @Test
    public void shouldDeleteCategory() {
        sut.deleteReceptsCategory(RECEPT_ID);

        verify(categoryRep).findByRecept(RECEPT_ID);
        verify(categoryRep, times(CATEGORY_LIST_SIZE)).delete(any());
    }

    @Test
    public void shouldFindTagsByRecept() {
        sut.findTagsByRecept(RECEPT_ID);

        verify(categoryRep).findByRecept(RECEPT_ID);
        verify(tagServ).findTag(any());
    }

    @Test
    public void shouldSaveCategory() {
        Integer categoryId = sut.saveCategory(RECEPT_ID, TAG_ID);

        verify(categoryRep).save(any());
        assertThat(categoryId, is(CATEGORY_ID));
    }

    @Test
    public void shouldDeleteTagFromRecept() {
        sut.deleteTagFromRecept(RECEPT_ID, TAG_ID);

        verify(categoryRep).delete(any());
    }

    private List<Category> createCategoryList(int categoryNumber) {
        List<Category> categoryList = new ArrayList<>();
        for (int i = 0; i < categoryNumber; i++) {
            categoryList.add(createCategory());
        }
        return categoryList;
    }

    private Category createCategory() {
        Category category = new Category();
        category.setReceptId(RECEPT_ID);
        category.setTagId(TAG_ID);
        return category;
    }
}