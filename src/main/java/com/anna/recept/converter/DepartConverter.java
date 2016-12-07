package com.anna.recept.converter;

import com.anna.recept.dto.DepartDto;
import com.anna.recept.persist.Dapart;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DepartConverter {

    public static DepartDto toDepartDto(Dapart entity){
        DepartDto departDto = new DepartDto();
        departDto.setId(entity.getId());
        departDto.setName(entity.getName());
        return departDto;
    }
}
