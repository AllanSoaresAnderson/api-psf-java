package br.com.psf.personalsystemfinance.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class EventualTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @Nonnull
    private Double value;
    @Column(nullable = false)
    @Nonnull
    private LocalDate date;
    private String type;

    @OneToOne(mappedBy = "eventualTransaction", fetch = FetchType.LAZY)
    private Transactions transactions;


    public EventualTransaction(){

    }

    public EventualTransaction(@Nonnull Double value, @Nonnull LocalDate date) {
        this.value = value;
        this.date = date;
    }
}
