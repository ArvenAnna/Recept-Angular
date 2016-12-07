package com.anna.recept.persist;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Proportion implements Serializable {
    private Integer id;
    private String norma;
    private Integer receptId;
    private Integer ingridientId;
}
