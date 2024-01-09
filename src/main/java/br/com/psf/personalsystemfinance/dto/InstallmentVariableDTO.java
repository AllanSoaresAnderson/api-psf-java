package br.com.psf.personalsystemfinance.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InstallmentVariableDTO {

    private Integer id;
    private Integer idFixedTransaction;
    private Double value;
    private LocalDate date;
}
