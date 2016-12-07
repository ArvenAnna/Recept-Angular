package com.anna.recept.persist;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class File implements Serializable {
    private Integer id;
    private byte[] fileContent;
}
