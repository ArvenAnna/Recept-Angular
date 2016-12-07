package com.anna.recept.service.impl;

import com.anna.recept.converter.ReferenceConverter;
import com.anna.recept.dto.ReferenceDto;
import com.anna.recept.repository.IReferenceRepository;
import com.anna.recept.service.IReceptService;
import com.anna.recept.service.IReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReferenceService implements IReferenceService {

    @Autowired
    private IReferenceRepository refRep;

    @Autowired
    private IReceptService receptServ;

    @Override
    public List<ReferenceDto> findReceptsReferences(Integer receptId) {
        return refRep.findByRecept(receptId).stream()
                .map(ref -> ReferenceConverter.toReferenceDto(ref, receptServ.getRecept(ref.getReceptReferenceId()).getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReceptReferences(Integer receptId) {
        refRep.findByRecept(receptId).stream().forEach(refRep::delete);
    }

    @Override
    public int saveReference(ReferenceDto reference, Integer receptId) {
        Assert.isNull(reference.getId());
        return refRep.save(ReferenceConverter.toReferenceEntity(reference, receptId));
    }

    @Override
    public void deleteReference(Integer id) {
        refRep.delete(refRep.findById(id));
    }
}
