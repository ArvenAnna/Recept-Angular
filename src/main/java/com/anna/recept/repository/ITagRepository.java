package com.anna.recept.repository;

import com.anna.recept.persist.Tag;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ITagRepository extends IJdbcTemplateCrudOperations<Tag> {

    Logger logger = Logger.getLogger(ITagRepository.class);

    @Override
    default Tag typeMapper(ResultSet rs) throws SQLException {
        Tag tag = new Tag();
        tag.setId(rs.getInt("id"));
        tag.setName(rs.getString("name"));
        return tag;
    }

    @Override
    default String getTable() {
        return "tag";
    }

    @Override
    default String getPkAsId() {
        return "id";
    }

    @Override
    default String getFieldTemplateAsString() {
        return "?";
    }

    @Override
    default String getFieldNamesAsString() {
        return "name";
    }

    @Override
    default PreparedStatement preparedStatementSetter(PreparedStatement ps, Tag tag) {
        try {
            ps.setString(1, tag.getName());
        } catch (SQLException e) {
            System.out.println("Repository error TAG");
        }
        return ps;
    }

    @Override
    default PreparedStatement updatePreparedStatementSetter(PreparedStatement ps, Tag tag) {
        try {
            preparedStatementSetter(ps, tag);
            ps.setInt(2, tag.getId());
        } catch (SQLException e) {
            logger.warn("Repository error TAG_UPDATE");
        }
        return ps;
    }

    @Override
    default PreparedStatement deletePreparedStatementSetter(PreparedStatement ps, Tag tag) {
        try {
            preparedStatementSetter(ps, tag);
            ps.setInt(1, tag.getId());
        } catch (SQLException e) {
            logger.warn("Repository error TAG_DELETE");
        }
        return ps;
    }

    @Override
    default String getUpdateColunmTemplate() {
        return "name = ?";
    }
}

