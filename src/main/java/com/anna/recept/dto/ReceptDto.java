package com.anna.recept.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ReceptDto {
    private Integer id;
    private String text;
    private String name;
    private DepartDto departId;
    private List<ProportionDto> proportions;
    private List<DetailDto> details;
    private List<ReferenceDto> references;
    private List<TagDto> tags;
    private String imgPath;
}
