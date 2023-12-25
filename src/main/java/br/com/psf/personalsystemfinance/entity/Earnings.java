package br.com.psf.personalsystemfinance.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Earnings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double value;
    private Double estimateValue;

    @OneToOne
    @JoinColumn(name = "idEntity")
    private Entities idEntity;

}
