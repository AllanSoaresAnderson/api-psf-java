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
    private String name;
    @Column(nullable = false)
    @Nonnull
    private Double value;
    @Column(nullable = false)
    @Nonnull
    private LocalDate date;
    private String type;


    public EventualTransaction(){

    }

    public EventualTransaction(@Nonnull String name, @Nonnull Double value, @Nonnull LocalDate date) {
        this.name = name;
        this.value = value;
        this.date = date;
    }
}
