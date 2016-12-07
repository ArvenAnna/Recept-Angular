package com.anna.recept.repository.impl;

import com.anna.recept.persist.Proportion;
import com.anna.recept.repository.IProportionRepository;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

@Getter
@Repository
public class ProportionRepository implements IProportionRepository {

    @Autowired
    private JdbcTemplate jdbc;

    private static final String FIND_BY_RECEPT = "SELECT * FROM proportion WHERE recept_id =?";

    @Override
    public List<Proportion> findByRecept(final Integer receptId) {
        return getFoundList(FIND_BY_RECEPT, ps->ps.setInt(1, receptId));
    }
}
