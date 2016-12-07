package com.anna.recept.repository.impl;

import com.anna.recept.persist.Dapart;
import com.anna.recept.repository.IDepartRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Getter
@Repository
public class DepartRepository implements IDepartRepository{
    
    @Autowired
    private JdbcTemplate jdbc;

    private static final String FIND_BY_RECEPT = "SELECT dep.id, dep.name FROM dapart dep, recept rec WHERE rec.depart_id = dep.id AND rec.id =?";

    @Override
    public Dapart findByRecept(Integer receptId) {
        return getFoundEntity(FIND_BY_RECEPT, ps->ps.setInt(1, receptId));
    }
}
