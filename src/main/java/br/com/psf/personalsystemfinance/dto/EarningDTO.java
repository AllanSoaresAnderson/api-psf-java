package br.com.psf.personalsystemfinance.dto;

import br.com.psf.personalsystemfinance.entity.Entities;
import lombok.Data;

@Data
public class EarningDTO {

    private Integer id;
    private String name;
    private Double value;
    private Double estimateValue;
    private Integer idEntity;
}
