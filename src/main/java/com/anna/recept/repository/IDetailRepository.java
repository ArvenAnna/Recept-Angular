package com.anna.recept.repository;

import com.anna.recept.persist.Detail;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IDetailRepository extends IJdbcTemplateCrudOperations<Detail> {

    Logger logger = Logger.getLogger(IDetailRepository.class);

    @Override
    default Detail typeMapper(ResultSet rs) throws SQLException {
        Detail detail = new Detail();
        detail.setId(rs.getInt("id"));
        detail.setDescription(rs.getString("description"));
        detail.setReceptId(rs.getInt("recept_id"));
        detail.setFilePath(rs.getString("file"));
        return detail;
    }

    @Override
    default String getTable() {
        return "detail";
    }

    @Override
    default String getPkAsId() {
        return "id";
    }

    @Override
    default String getFieldTemplateAsString() {
        return "?,?,?";
    }

    @Override
    default String getFieldNamesAsString() {
        return "description, recept_id, file";
    }

    @Override
    default PreparedStatement preparedStatementSetter(PreparedStatement ps, Detail detail) {
        try {
            ps.setString(1, detail.getDescription());
            ps.setInt(2, detail.getReceptId());
            ps.setString(3, detail.getFilePath());
        } catch (SQLException e) {
            logger.warn("Repository error DETAIL");
        }
        return ps;
    }

    @Override
    default PreparedStatement updatePreparedStatementSetter(PreparedStatement ps, Detail detail) {
        try {
            preparedStatementSetter(ps, detail);
            ps.setInt(4, detail.getId());
        } catch (SQLException e) {
            logger.warn("Repository error DETAIL_UPDATE");
        }
        return ps;
    }

    @Override
    default PreparedStatement deletePreparedStatementSetter(PreparedStatement ps, Detail detail) {
        try {
            preparedStatementSetter(ps, detail);
            ps.setInt(1, detail.getId());
        } catch (SQLException e) {
            logger.warn("Repository error DETAIL_DELETE");
        }
        return ps;
    }

    @Override
    default String getUpdateColunmTemplate() {
        return "description =?, recept_id =?, file=?";
    }

    List<Detail> findByRecept(Integer receptId);
}
