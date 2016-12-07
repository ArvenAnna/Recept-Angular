package com.anna.recept.repository.impl;

import com.anna.recept.persist.Reference;
import com.anna.recept.repository.IReferenceRepository;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Getter
@Repository
public class ReferenceRepository implements IReferenceRepository{
    @Autowired
    private JdbcTemplate jdbc;

    private static final String FIND_BY_RECEPT = "SELECT * FROM reference WHERE recept_id =?";

    @Override
    public List<Reference> findByRecept(final Integer receptId) {
        return getFoundList(FIND_BY_RECEPT, ps->ps.setInt(1, receptId));
    }
}
