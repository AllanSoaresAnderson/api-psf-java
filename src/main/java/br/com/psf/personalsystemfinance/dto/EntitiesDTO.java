package br.com.psf.personalsystemfinance.dto;

import lombok.Data;

@Data
public class EntitiesDTO {
    private Integer id;
    private String name;
    private Boolean isPerson;
    private Integer idChildren;
}
