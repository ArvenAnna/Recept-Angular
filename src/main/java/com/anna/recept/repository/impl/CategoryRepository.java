package com.anna.recept.repository.impl;

import com.anna.recept.persist.Category;
import com.anna.recept.repository.ICategoryRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Getter
@Repository
public class CategoryRepository implements ICategoryRepository {

    @Autowired
    private JdbcTemplate jdbc;

    private static final String FIND_BY_RECEPT = "SELECT * FROM category WHERE recept_id =?";

    @Override
    public List<Category> findByRecept(Integer receptId) {
        return getFoundList(FIND_BY_RECEPT, ps->ps.setInt(1, receptId));
    }
}
