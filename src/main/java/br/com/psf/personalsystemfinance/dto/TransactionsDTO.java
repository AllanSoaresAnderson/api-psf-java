package br.com.psf.personalsystemfinance.dto;

import lombok.Data;

@Data
public class TransactionsDTO {
    private Integer id;
    private String name;
    private Double value;
    private String type;
    private String categoryType;
    private Integer idCategory;
}
