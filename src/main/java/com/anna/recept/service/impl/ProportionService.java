package com.anna.recept.service.impl;

import com.anna.recept.converter.ProportionConverter;
import com.anna.recept.dto.ProportionDto;
import com.anna.recept.exception.Errors;
import com.anna.recept.repository.IProportionRepository;

import java.util.List;

import com.anna.recept.service.IIngridientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anna.recept.service.IProportionService;
import org.springframework.util.Assert;

import java.util.stream.Collectors;

@Service
public class ProportionService implements IProportionService {

    @Autowired
    private IProportionRepository propRep;
    @Autowired
    private IIngridientService ingServ;

    @Override
    public List<ProportionDto> findReceptsProportions(Integer receptId) {
        return propRep.findByRecept(receptId).stream()
                .map(prop -> ProportionConverter.toProportionDto(prop, ingServ.findIngridient(prop.getIngridientId())))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProportions(Integer receptId) {
        propRep.findByRecept(receptId).stream().forEach(propRep::delete);
    }

    @Override
    public int saveProportion(ProportionDto proportion, Integer receptId) {
        Assert.notNull(proportion.getIngridient(), Errors.NOT_NULL.getCause());
        Assert.notNull(proportion.getIngridient().getId(), Errors.NOT_NULL.getCause());

        if(proportion.getId() == null) {
            return propRep.save(ProportionConverter.toProportionEntity(proportion, receptId));
        }

        propRep.update(ProportionConverter.toProportionEntity(proportion, receptId));
        return proportion.getId();
    }

    @Override
    public void deleteProportion(Integer id) {
        propRep.delete(propRep.findById(id));
    }

}
