package br.com.psf.personalsystemfinance.dto;

import lombok.Data;

@Data
public class ExpensesDTO {
    private Integer id;
    private String name;
    private Double estimateValue;
    private Double currentValue;
    private Integer idEntity;
}
