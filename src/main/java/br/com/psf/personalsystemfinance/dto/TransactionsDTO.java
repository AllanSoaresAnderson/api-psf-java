package br.com.psf.personalsystemfinance.dto;

import lombok.Data;

@Data
public class TransactionsDTO {
    private Integer id;
    private String name;
    private String type;
    private String categoryType;
    private Integer idCategory;
    private Integer idEntity;
}
