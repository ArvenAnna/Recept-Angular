package com.anna.recept.converter;

import com.anna.recept.dto.ReceptDto;
import com.anna.recept.persist.Recept;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReceptConverter {

    public static Recept toReceptEntity(ReceptDto receptDto) {
        Recept recept = new Recept();
        recept.setId(receptDto.getId());
        recept.setName(receptDto.getName());
        recept.setText(receptDto.getText());
        recept.setDepartId(receptDto.getDepartId().getId());
        recept.setImgPath(receptDto.getImgPath());
        return recept;
    }

    public static ReceptDto toReceptDto(Recept entity) {
        ReceptDto recept = new ReceptDto();
        recept.setId(entity.getId());
        recept.setName(entity.getName());
        recept.setText(entity.getText());
        recept.setImgPath(entity.getImgPath());
        return recept;
    }

}
