package br.com.psf.personalsystemfinance.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Transactions {

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
    private String type;
    @Column(nullable = false)
    @Nonnull
    private String categoryType;
    @Column(nullable = false)
    @Nonnull
    private Integer idCategory;
    @Column(nullable = false)
    @Nonnull
    private Integer idEntity;

    public Transactions(@Nonnull String name,
                        @Nonnull Double value,
                        @Nonnull String type,
                        @Nonnull String categoryType,
                        @Nonnull Integer idCategory,
                        @Nonnull Integer idEntity)
    {
        this.name = name;
        this.value = value;
        this.type = type;
        this.categoryType = categoryType;
        this.idCategory = idCategory;
        this.idEntity = idEntity;
    }
}
