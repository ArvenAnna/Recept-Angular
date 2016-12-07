package com.anna.recept.repository.impl;

import com.anna.recept.repository.ITagRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Getter
@Repository
public class TagRepository implements ITagRepository {

    @Autowired
    private JdbcTemplate jdbc;
}
