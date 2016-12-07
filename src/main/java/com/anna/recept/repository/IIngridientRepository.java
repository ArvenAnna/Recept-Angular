package com.anna.recept.repository;

import com.anna.recept.persist.Ingridient;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IIngridientRepository extends IJdbcTemplateCrudOperations<Ingridient> {

    Logger logger = Logger.getLogger(IIngridientRepository.class);

    @Override
    default Ingridient typeMapper(ResultSet rs) throws SQLException {
        Ingridient ingridient = new Ingridient();
        ingridient.setId(rs.getInt("id"));
        ingridient.setName(rs.getString("name"));
        return ingridient;
    }

    @Override
    default String getTable() {
        return "ingridient";
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
    default PreparedStatement preparedStatementSetter(PreparedStatement ps, Ingridient ing) {
        try {
            ps.setString(1, ing.getName());
        } catch (SQLException e) {
            logger.warn("Repository error INGRIDIENT");
        }
        return ps;
    }

    @Override
    default PreparedStatement updatePreparedStatementSetter(PreparedStatement ps, Ingridient ing) {
        try {
            preparedStatementSetter(ps, ing);
            ps.setInt(2, ing.getId());
        } catch (SQLException e) {
            logger.warn("Repository error INGRIDIENT_UPDATE");
        }
        return ps;
    }

    @Override
    default PreparedStatement deletePreparedStatementSetter(PreparedStatement ps, Ingridient ing) {
        try {
            preparedStatementSetter(ps, ing);
            ps.setInt(1, ing.getId());
        } catch (SQLException e) {
            logger.warn("Repository error INGRIDIENT_DELETE");
        }
        return ps;
    }

    @Override
    default String getUpdateColunmTemplate() {
        return "name = ?";
    }

    Ingridient findByName(String name);
}
