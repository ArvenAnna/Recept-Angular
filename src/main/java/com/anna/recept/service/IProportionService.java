package com.anna.recept.service;

import com.anna.recept.dto.ProportionDto;

import java.util.List;

public interface IProportionService {

    List<ProportionDto> findReceptsProportions(Integer receptId);

    void deleteProportions(Integer receptId);

    int saveProportion(ProportionDto proportion, Integer receptId);

    void deleteProportion(Integer id);
}
