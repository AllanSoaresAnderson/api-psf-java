package br.com.psf.personalsystemfinance.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @Nonnull
    private String type;
    @Column(nullable = false)
    @Nonnull
    private Integer idTransaction;
    @Column(nullable = false)
    private boolean itsDone;
    @Column(nullable = false)
    @Nonnull
    private LocalDate date;
    @Column(nullable = false)
    @Nonnull
    private Double value;

}
