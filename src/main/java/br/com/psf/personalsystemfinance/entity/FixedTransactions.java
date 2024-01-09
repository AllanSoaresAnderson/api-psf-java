package br.com.psf.personalsystemfinance.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
public class FixedTransactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @Nonnull
    private String type;
    private Integer amountTime;
    @Column(nullable = false)
    @Nonnull
    private LocalDate startDate;
    private LocalDate endDate;
    @Column(nullable = false)
    private boolean isInstallment;
    private Integer amountInstallment;
    private String typeInstallment;
    @Column(nullable = false)
    @Nonnull
    private Double value;

    public FixedTransactions(
            @Nonnull String type,
            @Nonnull LocalDate startDate,
            @Nonnull Double value
            ){
        this.type = type;
        this.startDate = startDate;
        this.value = value;

    }

}
