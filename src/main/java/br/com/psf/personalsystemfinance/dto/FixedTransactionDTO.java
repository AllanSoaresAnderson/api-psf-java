package br.com.psf.personalsystemfinance.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class FixedTransactionDTO {
    private Integer id;
    private String type;
    private Integer amountTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isInstallment;
    private Integer amountInstallment;
    private Double value;
}
