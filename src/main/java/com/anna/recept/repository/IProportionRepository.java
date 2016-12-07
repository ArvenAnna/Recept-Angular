package com.anna.recept.repository;

import com.anna.recept.persist.Proportion;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IProportionRepository extends IJdbcTemplateCrudOperations<Proportion> {

    Logger logger = Logger.getLogger(IProportionRepository.class);

    @Override
    default Proportion typeMapper(ResultSet rs) throws SQLException {
        Proportion proportion = new Proportion();
        proportion.setId(rs.getInt("id"));
        proportion.setNorma(rs.getString("norma"));
        proportion.setReceptId(rs.getInt("recept_id"));
        proportion.setIngridientId(rs.getInt("ingridient_id"));
        return proportion;
    }

    @Override
    default String getTable() {
        return "proportion";
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
        return "norma, ingridient_id, recept_id";
    }

    @Override
    default PreparedStatement preparedStatementSetter(PreparedStatement ps, Proportion proportion) {
        try {
            ps.setString(1, proportion.getNorma());
            ps.setInt(2, proportion.getIngridientId());
            ps.setInt(3, proportion.getReceptId());
        } catch (SQLException e) {
            logger.warn("Repository error PROPORTION");
        }
        return ps;
    }

    @Override
    default PreparedStatement updatePreparedStatementSetter(PreparedStatement ps, Proportion proportion) {
        try {
            preparedStatementSetter(ps, proportion);
            ps.setInt(4, proportion.getId());
        } catch (SQLException e) {
            logger.warn("Repository error PROPORTION_UPDATE");
        }
        return ps;
    }

    @Override
    default PreparedStatement deletePreparedStatementSetter(PreparedStatement ps, Proportion proportion) {
        try {
            preparedStatementSetter(ps, proportion);
            ps.setInt(1, proportion.getId());
        } catch (SQLException e) {
            logger.warn("Repository error PROPORTION_DELETE");
        }
        return ps;
    }

    @Override
    default String getUpdateColunmTemplate() {
        return "norma =?, ingridient_id =?, recept_id =?";
    }

    List<Proportion> findByRecept(Integer receptId);
}
