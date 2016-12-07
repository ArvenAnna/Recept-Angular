package com.anna.recept.repository;

import com.anna.recept.persist.Reference;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IReferenceRepository extends IJdbcTemplateCrudOperations<Reference> {

    Logger logger = Logger.getLogger(IReferenceRepository.class);

    @Override
    default Reference typeMapper(ResultSet rs) throws SQLException {
        Reference reference = new Reference();
        reference.setId(rs.getInt("id"));
        reference.setReceptId(rs.getInt("recept_id"));
        reference.setReceptReferenceId(rs.getInt("recept_reference_id"));
        return reference;
    }

    @Override
    default String getTable() {
        return "reference";
    }

    @Override
    default String getPkAsId() {
        return "id";
    }

    @Override
    default String getFieldTemplateAsString() {
        return "?,?";
    }

    @Override
    default String getFieldNamesAsString() {
        return "recept_id, recept_reference_id";
    }

    @Override
    default PreparedStatement preparedStatementSetter(PreparedStatement ps, Reference reference) {
        try {
            ps.setInt(1, reference.getReceptId());
            ps.setInt(2, reference.getReceptReferenceId());
        } catch (SQLException e) {
            logger.warn("Repository error REFERENCE");
        }
        return ps;
    }

    @Override
    default PreparedStatement updatePreparedStatementSetter(PreparedStatement ps, Reference reference) {
        try {
            preparedStatementSetter(ps, reference);
            ps.setInt(3, reference.getId());
        } catch (SQLException e) {
            logger.warn("Repository error REFERENCE_UPDATE");
        }
        return ps;
    }

    @Override
    default PreparedStatement deletePreparedStatementSetter(PreparedStatement ps, Reference reference) {
        try {
            preparedStatementSetter(ps, reference);
            ps.setInt(1, reference.getId());
        } catch (SQLException e) {
            logger.warn("Repository error REFERENCE_DELETE");
        }
        return ps;
    }

    @Override
    default String getUpdateColunmTemplate() {
        return "recept_id =?, recept_reference_id =?";
    }

    List<Reference> findByRecept(Integer receptId);
}
