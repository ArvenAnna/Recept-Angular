package com.anna.recept.facade.impl;

import com.anna.recept.dto.ReceptDto;
import com.anna.recept.facade.IReceptFullFacade;
import com.anna.recept.service.ICategoryService;
import com.anna.recept.service.IDepartService;
import com.anna.recept.service.IDetailService;
import com.anna.recept.service.IProportionService;
import com.anna.recept.service.IReceptService;
import com.anna.recept.service.IReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceptFullFacade implements IReceptFullFacade {

    @Autowired
    private IReceptService receptServ;

    @Autowired
    private IDepartService departServ;

    @Autowired
    private IProportionService propServ;

    @Autowired
    private IDetailService detServ;

    @Autowired
    private IReferenceService refServ;

    @Autowired
    private ICategoryService categoryServ;

    @Override
    public ReceptDto showRecept(Integer receptId) {
        ReceptDto recept = receptServ.getRecept(receptId);
        recept.setProportions(propServ.findReceptsProportions(receptId));
        recept.setReferences(refServ.findReceptsReferences(receptId));
        recept.setDetails(detServ.findReceptsDetails(receptId));
        recept.setDepartId(departServ.findDepartmentByReceptId(receptId));
        recept.setTags(categoryServ.findTagsByRecept(receptId));
        return recept;
    }

    @Override
    public void deleteRecept(Integer receptId) {
        categoryServ.deleteReceptsCategory(receptId);
        propServ.deleteProportions(receptId);
        detServ.deleteDetails(receptId);
        refServ.deleteReceptReferences(receptId);
        receptServ.deleteRecept(receptId);
    }

    @Override
    public ReceptDto findReceptByDetailId(Integer detailId) {
        Integer receptId = detServ.findDetail(detailId).getReceptId();
        return receptServ.getRecept(receptId);
    }
}
