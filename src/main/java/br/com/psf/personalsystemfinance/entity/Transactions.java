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
    private String type;
    @Column(nullable = false)
    @Nonnull
    private String categoryType;
    @Column(nullable = false)
    @Nonnull
    private Integer idCategory;
    @Column(insertable = false, updatable = false)
    @Nonnull
    private Integer idEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEntity", referencedColumnName = "id")
    private Entities entity;

    public Transactions(){}

    public Transactions(
                        @Nonnull String type,
                        @Nonnull String categoryType,
                        @Nonnull Integer idCategory,
                        @Nonnull Integer idEntity)
    {
        this.type = type;
        this.categoryType = categoryType;
        this.idCategory = idCategory;
        this.idEntity = idEntity;
    }
}
