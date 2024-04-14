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
    private String name;
    @Column(nullable = false)
    @Nonnull
    private String categoryType;
    @Column
    private Integer idFixedTransaction;
    @Column
    private Integer idEventualTransaction;
    @Column(insertable = false, updatable = false)
    @Nonnull
    private Integer idEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEntity", referencedColumnName = "id")
    private Entities entity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idFixedTransaction", referencedColumnName = "id", insertable = false, updatable = false)
    private FixedTransactions fixedTransactions;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEventualTransaction", referencedColumnName = "id", insertable = false, updatable = false)
    private EventualTransaction eventualTransaction;

    public Transactions(){}

    public Transactions(
                        @Nonnull String name,
                        @Nonnull String type,
                        @Nonnull String categoryType,
                        @Nonnull Integer idEntity)
    {
        this.name = name;
        this.type = type;
        this.categoryType = categoryType;
        this.idEntity = idEntity;
    }
}
