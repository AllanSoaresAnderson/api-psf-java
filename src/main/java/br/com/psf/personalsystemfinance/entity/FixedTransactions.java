package br.com.psf.personalsystemfinance.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class FixedTransactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @Nonnull
    private String type;
    @Column
    private Integer amountTime;
    @Column(nullable = false)
    @Nonnull
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
    @Column(nullable = false)
    private Boolean isInstallment;
    @Column
    private Integer amountInstallment;
    @Column(nullable = false)
    @Nonnull
    private Double value;

    @OneToOne(mappedBy = "fixedTransactions", fetch = FetchType.LAZY)
    private Transactions transactions;

    public FixedTransactions(){

    }
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
