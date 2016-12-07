package com.anna.recept.service;

import com.anna.recept.dto.ReceptDto;

import java.util.List;

public interface IReceptService {
    List<ReceptDto> showReceptDtos(Integer departId);

    ReceptDto getRecept(Integer receptId);

    void deleteRecept(Integer receptId);

    Integer saveRecept(ReceptDto recept);

    Integer saveWithUniqueName(ReceptDto recept);

    void saveFilePath(Integer receptId, String path);

    List<ReceptDto> showReceptsByTag(Integer tagId);

    ReceptDto getRecept(String name);

}
