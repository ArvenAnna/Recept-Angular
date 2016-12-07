package com.anna.recept.service.impl;

import com.anna.recept.converter.ReceptConverter;
import com.anna.recept.dto.ReceptDto;
import com.anna.recept.exception.Errors;
import com.anna.recept.exception.ReceptApplicationException;
import com.anna.recept.persist.Recept;
import com.anna.recept.repository.IReceptRepository;
import com.anna.recept.service.IAutoCompleteService;
import com.anna.recept.service.IReceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceptService implements IReceptService {

    private static final Integer ALL_RECEPTS_FETCH_FLAG = -1;

    @Autowired
    private IReceptRepository receptRep;

    @Autowired
    private IAutoCompleteService autocompleteService;

    @Override
    public List<ReceptDto> showReceptDtos(Integer departId) {
        return showRecepts(departId).stream()
                .map(recept -> ReceptConverter.toReceptDto(recept))
                .collect(Collectors.toList());
    }

    private List<Recept> showRecepts(Integer departId) {
        if (ALL_RECEPTS_FETCH_FLAG.equals(departId)) {
            return receptRep.findAll();
        } else {
            return receptRep.findByDepart(departId);
        }
    }

    @Override
    public ReceptDto getRecept(Integer receptId) {
        return ReceptConverter.toReceptDto(receptRep.findById(receptId));
    }

    @Override
    public void deleteRecept(Integer receptId) {
        receptRep.delete(receptRep.findById(receptId));
    }

    @Override
    public Integer saveRecept(ReceptDto recept) {
        //move to transaction
        autocompleteService.addReference(recept.getName());
        if (recept.getId() == null) {
            return receptRep.save(ReceptConverter.toReceptEntity(recept));
        } else {
            autocompleteService.removeReference(receptRep.findById(recept.getId()).getName());
            receptRep.update(ReceptConverter.toReceptEntity(recept));
            return recept.getId();
        }
    }

    @Override
    public Integer saveWithUniqueName(ReceptDto recept) {
        if (isUniqueReceptName(recept.getName())) {
            return saveRecept(recept);
        }
        throw new ReceptApplicationException(Errors.RECEPT_NAME_NOT_UNIQUE);
    }

    @Override
    public void saveFilePath(Integer receptId, String path) {
        Recept recept = receptRep.findById(receptId);
        recept.setImgPath(path);
        receptRep.update(recept);
    }

    @Override
    public List<ReceptDto> showReceptsByTag(Integer tagId) {
        return receptRep.findByTag(tagId).stream()
                .map(recept -> ReceptConverter.toReceptDto(recept))
                .collect(Collectors.toList());
    }

    @Override
    public ReceptDto getRecept(String name) {
        Recept recept = receptRep.findByName(name);
        if(recept != null) {
            return ReceptConverter.toReceptDto(recept);
        }
        return null;
    }

    private boolean isUniqueReceptName(String name) {
        return receptRep.findByName(name) == null;
    }
}
