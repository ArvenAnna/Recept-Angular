package com.anna.recept.service;

import com.anna.recept.dto.DetailDto;

import java.util.List;

public interface IDetailService {
    List<DetailDto> findReceptsDetails(Integer receptId);

    void deleteDetails(Integer receptId);

    int saveDetail(DetailDto detail);

    void deleteDetail(Integer id);

    void saveFilePath(String path, Integer detailId);

    DetailDto findDetail(Integer detailId);
}
