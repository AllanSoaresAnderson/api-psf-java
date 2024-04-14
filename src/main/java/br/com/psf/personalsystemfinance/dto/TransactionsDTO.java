package br.com.psf.personalsystemfinance.dto;

import lombok.Data;

@Data
public class TransactionsDTO {
    private Integer id;
    private String type;
    private String name;
    private String categoryType;
    private Integer idFixedTransaction;
    private Integer idEventualTransaction;
    private Integer idEntity;
    private EntitiesDTO entity;
    private FixedTransactionDTO fixedTransaction;
    private EventualTransactionDTO eventualTransaction;
}
