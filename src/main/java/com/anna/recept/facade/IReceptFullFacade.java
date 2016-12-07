package com.anna.recept.facade;

import com.anna.recept.dto.ReceptDto;

public interface IReceptFullFacade {

    ReceptDto showRecept(Integer receptId);

    void deleteRecept(Integer receptId);

    ReceptDto findReceptByDetailId(Integer detailId);
}
