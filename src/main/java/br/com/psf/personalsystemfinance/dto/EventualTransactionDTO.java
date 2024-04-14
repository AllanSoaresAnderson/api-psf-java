package br.com.psf.personalsystemfinance.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class EventualTransactionDTO {
    private Integer id;
    private Double value;
    private LocalDate date;
    private String type;
}
