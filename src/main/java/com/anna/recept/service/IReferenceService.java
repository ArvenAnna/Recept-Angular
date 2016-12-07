package com.anna.recept.service;

import com.anna.recept.dto.ReferenceDto;

import java.util.List;

public interface IReferenceService {
    List<ReferenceDto> findReceptsReferences(Integer receptId);

    void deleteReceptReferences(Integer receptId);

    int saveReference(ReferenceDto reference, Integer receptId);

    void deleteReference(Integer id);
}
