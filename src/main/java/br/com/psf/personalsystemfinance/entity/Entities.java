package br.com.psf.personalsystemfinance.entity;


import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Entities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @Nonnull
    private String name;
    @Column(nullable = false)
    @Nonnull
    private Boolean isPerson;
    private Integer idChildren;


    /*
    *
    * Relationship
    *
    * */

    @OneToMany(mappedBy = "entity", fetch = FetchType.LAZY)
    private List<Transactions> transactions;

    public Entities(){

    }

    public Entities(@Nonnull String name, @Nonnull Boolean isPerson) {
        this.name = name;
        this.isPerson = isPerson;
    }
}
