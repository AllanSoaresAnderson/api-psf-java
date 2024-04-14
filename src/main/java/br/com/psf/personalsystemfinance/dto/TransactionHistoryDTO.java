package br.com.psf.personalsystemfinance.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class TransactionHistoryDTO {
    private Integer id;
    private String type;
    private Integer idTransaction;
    private Boolean itsDone;
    private LocalDate date;
    private Double value;
}
