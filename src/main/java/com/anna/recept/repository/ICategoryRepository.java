package com.anna.recept.repository;

import com.anna.recept.persist.Category;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ICategoryRepository extends IJdbcTemplateCrudOperations<Category> {

    Logger logger = Logger.getLogger(ICategoryRepository.class);

    @Override
    default Category typeMapper(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getInt("id"));
        category.setTagId(rs.getInt("tag_id"));
        category.setReceptId(rs.getInt("recept_id"));
        return category;
    }

    @Override
    default String getTable() {
        return "category";
    }

    @Override
    default String getPkAsId() {
        return "id";
    }

    @Override
    default String getFieldTemplateAsString() {
        return "?, ?";
    }

    @Override
    default String getFieldNamesAsString() {
        return "recept_id, tag_id";
    }

    @Override
    default PreparedStatement preparedStatementSetter(PreparedStatement ps, Category category) {
        try {
            ps.setInt(1, category.getReceptId());
            ps.setInt(2, category.getTagId());
        } catch (SQLException e) {
            logger.warn("Repository error CATEGORY");
        }
        return ps;
    }

    @Override
    default PreparedStatement updatePreparedStatementSetter(PreparedStatement ps, Category category) {
        try {
            preparedStatementSetter(ps, category);
            ps.setInt(3, category.getId());
        } catch (SQLException e) {
            logger.warn("Repository error CATEGORY_UPDATE");
        }
        return ps;
    }

    @Override
    default PreparedStatement deletePreparedStatementSetter(PreparedStatement ps, Category category) {
        try {
            preparedStatementSetter(ps, category);
            ps.setInt(1, category.getId());
        } catch (SQLException e) {
            logger.warn("Repository error CATEGORY_DELETE");
        }
        return ps;
    }

    @Override
    default String getUpdateColunmTemplate() {
        return "recept_id = ?, tag_id = ?";
    }

    List<Category> findByRecept(Integer receptId);
}
