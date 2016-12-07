package com.anna.recept.repository.impl;

import com.anna.recept.persist.Ingridient;
import com.anna.recept.repository.IIngridientRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Getter
@Repository
public class IngridientRepository implements IIngridientRepository{
    
    @Autowired
    private JdbcTemplate jdbc;

    private static final String FIND_BY_NAME = "SELECT * FROM ingridient WHERE name =?";

    @Override
    public Ingridient findByName(String name) {
        return getFoundEntity(FIND_BY_NAME, ps->ps.setString(1, name));
    }
}
