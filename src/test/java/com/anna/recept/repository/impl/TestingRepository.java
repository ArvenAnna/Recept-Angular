package com.anna.recept.repository.impl;

import com.anna.recept.repository.ITestingRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Hanna_Sira on 7/21/2016.
 */
@Repository
@Getter
public class TestingRepository implements ITestingRepository {

    @Autowired
    public JdbcTemplate jdbc;
}
