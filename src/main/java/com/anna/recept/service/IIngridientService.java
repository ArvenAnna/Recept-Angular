package com.anna.recept.service;

import com.anna.recept.dto.IngridientDto;

import java.util.List;

public interface IIngridientService {
    Integer saveIngridient(IngridientDto ingridient);

    Integer saveUniqueIngridient(IngridientDto ingridient);

    void deleteIngridient(Integer ingId);

    IngridientDto findIngridient(Integer ingridientId);

    List<IngridientDto> showAllIngridients();
}
