package br.com.psf.personalsystemfinance.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class InstallmentVariable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @Nonnull
    private Integer idFixedTransaction;
    @Column(nullable = false)
    @Nonnull
    private Double value;
    @Column(nullable = false)
    @Nonnull
    private LocalDate date;

    public InstallmentVariable(@Nonnull Integer idFixedTransaction, @Nonnull Double value, @Nonnull LocalDate date) {
        this.idFixedTransaction = idFixedTransaction;
        this.value = value;
        this.date = date;
    }
}
