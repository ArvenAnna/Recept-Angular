package com.anna.recept.converter;

import com.anna.recept.dto.DetailDto;
import com.anna.recept.persist.Detail;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DetailConverter {

    public static Detail toDetailEntity(DetailDto detailDto){
        Detail detail = new Detail();
        detail.setId(detailDto.getId());
        detail.setDescription(detailDto.getDescription());
        detail.setReceptId(detailDto.getReceptId());
        detail.setFilePath(detailDto.getFilePath());
        return detail;
    }

    public static DetailDto toDetailDto(Detail entity) {
        DetailDto detailDto = new DetailDto();
        detailDto.setId(entity.getId());
        detailDto.setDescription(entity.getDescription());
        detailDto.setReceptId(entity.getReceptId());
        detailDto.setFilePath(entity.getFilePath());
        return detailDto;
    }
}
