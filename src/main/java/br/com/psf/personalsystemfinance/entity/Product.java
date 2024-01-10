package br.com.psf.personalsystemfinance.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @Nonnull
    private String name;
    @Column(nullable = false)
    @Nonnull
    private Integer unitValue;

    public Product(@Nonnull String name, @Nonnull Integer unitValue) {
        this.name = name;
        this.unitValue = unitValue;
    }
}
