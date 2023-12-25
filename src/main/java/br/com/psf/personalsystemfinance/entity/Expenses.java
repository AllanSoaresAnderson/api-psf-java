package br.com.psf.personalsystemfinance.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50)
    private String name;
    private Double estimateValue;
    private Double currentValue;

    @OneToOne
    @JoinColumn(name = "idEntity")
    private Entities idEntity;
}
