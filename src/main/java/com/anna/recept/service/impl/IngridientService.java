package com.anna.recept.service.impl;

import com.anna.recept.converter.IngridientConverter;
import com.anna.recept.dto.IngridientDto;
import com.anna.recept.exception.Errors;
import com.anna.recept.exception.ReceptApplicationException;
import com.anna.recept.repository.IIngridientRepository;
import com.anna.recept.service.IAutoCompleteService;
import com.anna.recept.service.IIngridientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngridientService implements IIngridientService {

    @Autowired
    private IIngridientRepository ingRep;

    @Autowired
    private IAutoCompleteService autocompleteService;

    @Override
    public Integer saveIngridient(IngridientDto ingridient) {
        Assert.isNull(ingridient.getId(), Errors.ID_MUST_BE_NULL.getCause());
        //move to transaction
        autocompleteService.addIngridient(ingridient.getName());
        return ingRep.save(IngridientConverter.toIngridientEntity(ingridient));
    }

    @Override
    public Integer saveUniqueIngridient(IngridientDto ingridient) {
        if (isUniqueIngridientName(ingridient.getName())) {
            return saveIngridient(ingridient);
        }
        throw new ReceptApplicationException(Errors.INGRIDIENT_NAME_NOT_UNIQUE);
    }

    private boolean isUniqueIngridientName(String name) {
        return ingRep.findByName(name) == null;
    }

    @Override
    public void deleteIngridient(Integer ingId) {
        autocompleteService.removeIngridient(ingRep.findById(ingId).getName());
        ingRep.delete(ingRep.findById(ingId));
    }

    @Override
    public IngridientDto findIngridient(Integer ingridientId) {
        return IngridientConverter.toIngridientDto(ingRep.findById(ingridientId));
    }

    @Override
    public List<IngridientDto> showAllIngridients() {
        return ingRep.findAll().stream().map(ing -> IngridientConverter.toIngridientDto(ing))
                .collect(Collectors.toList());
    }

}
