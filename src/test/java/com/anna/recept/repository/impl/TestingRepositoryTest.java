package com.anna.recept.repository.impl;

import com.anna.recept.persist.TestTableEntity;
import com.anna.recept.repository.ITestingRepository;
import com.anna.recept.repository.RepositoryTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TestingRepositoryTest extends RepositoryTest {

    @Autowired
    private ITestingRepository sut;

    private static final String TEXT = "some text";

    @Test
    public void shouldSaveAndFind() {
        TestTableEntity table = constructTable();
        int id = sut.save(table);

        assertNotNull(sut.findById(id));
        assertThat(sut.findById(id).getText(), is(TEXT));
    }

    @Test
    public void shouldUpdate() {
        TestTableEntity table = constructTable();
        int id = sut.save(table);
        table.setId(id);
        String newText = "new text";
        table.setText(newText);

        sut.update(table);

        assertNotNull(sut.findById(id));
        assertThat(sut.findById(id).getText(), is(newText));
    }

    @Test
    public void shouldUpdateNotExistingWithoutException() {
        TestTableEntity table = constructTable();
        int id = sut.save(table);
        table.setId(id);
        sut.delete(table);
        String newText = "new text";
        table.setText(newText);

        sut.update(table);

        assertNull(sut.findById(id));
    }

    @Test
    public void shouldDelete() {
        TestTableEntity table = constructTable();
        int id = sut.save(table);
        table.setId(id);

        sut.delete(table);

        assertNull(sut.findById(id));
    }

    @Test
    public void shouldDeleteNotExistingWithoutException() {
        TestTableEntity table = constructTable();
        int id = sut.save(table);
        table.setId(id);
        sut.delete(table);

        sut.delete(table);

        assertNull(sut.findById(id));
    }

    @Test
    public void shouldFindAll() {
        TestTableEntity table1 = constructTable();
        TestTableEntity table2 = constructTable();
        sut.save(table1);
        sut.save(table2);

        List<TestTableEntity> tableList = sut.findAll();

        assertNotNull(tableList);
        assertThat(tableList.size(), greaterThan(1));
    }

    @Test
    public void shouldDeleteAll() {
        TestTableEntity table = constructTable();
        sut.save(table);

        sut.deleteAll();

        assertThat(sut.findAll(), empty());
    }

    @Test
    public void shouldDeleteEmptyTableWithoutException() {
        sut.deleteAll();
        sut.deleteAll();

        assertThat(sut.findAll(), empty());
    }

    private TestTableEntity constructTable(){
        TestTableEntity table = new TestTableEntity();
        table.setText(TEXT);
        return table;
    }
}
