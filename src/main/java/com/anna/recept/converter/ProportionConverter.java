package com.anna.recept.converter;

import com.anna.recept.dto.IngridientDto;
import com.anna.recept.dto.ProportionDto;
import com.anna.recept.persist.Proportion;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProportionConverter {

    public static Proportion toProportionEntity(ProportionDto propDto, Integer receptId){
        Proportion prop = new Proportion();
        prop.setId(propDto.getId());
        prop.setNorma(propDto.getNorma());
        prop.setIngridientId(propDto.getIngridient().getId());
        prop.setReceptId(receptId);
        return prop;
    }

    public static ProportionDto toProportionDto(Proportion entity, IngridientDto ingridient){
        ProportionDto proportionDto = new ProportionDto();
        proportionDto.setId(entity.getId());
        proportionDto.setNorma(entity.getNorma());
        proportionDto.setIngridient(ingridient);
        return proportionDto;
    }
}
