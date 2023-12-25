package br.com.psf.personalsystemfinance.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Entities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50)
    private String name;
    private Boolean isPerson;
}
