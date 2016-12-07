package com.anna.recept.service.impl;

import com.anna.recept.converter.DetailConverter;
import com.anna.recept.dto.DetailDto;
import com.anna.recept.persist.Detail;
import com.anna.recept.repository.IDetailRepository;
import com.anna.recept.service.IDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetailService implements IDetailService {

    @Autowired
    private IDetailRepository detRep;

    @Override
    public List<DetailDto> findReceptsDetails(Integer receptId) {
        return detRep.findByRecept(receptId).stream()
                .map(detail -> DetailConverter.toDetailDto(detail))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDetails(Integer receptId) {
        detRep.findByRecept(receptId).stream().forEach(detRep::delete);
    }

    @Override
    public int saveDetail(DetailDto detail) {
        if (detail.getId() == null) {
            return detRep.save(DetailConverter.toDetailEntity(detail));
        }

        detRep.update(DetailConverter.toDetailEntity(detail));
        return detail.getId();
    }

    @Override
    public void deleteDetail(Integer id) {
        detRep.delete(detRep.findById(id));
    }

    @Override
    public void saveFilePath(String path, Integer detailId) {
        Detail detail = detRep.findById(detailId);
        detail.setFilePath(path);
        detRep.update(detail);
    }

    @Override
    public DetailDto findDetail(Integer detailId) {
        return DetailConverter.toDetailDto(detRep.findById(detailId));
    }
}
