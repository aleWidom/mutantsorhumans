package com.mutantorhuman.mercadolibre.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter

@Entity
public class Dna {
    @Id
    @Column(unique = true)
    private String dna;
    private String isMutant;



    public Dna() {
    }

    public Dna(String dna, String isMutant) {
        this.dna = dna;
        this.isMutant = isMutant;
    }

    @Override
    public String toString() {
        return "Dna{" +
                "dna='" + dna + '\'' +
                ", isMutant='" + isMutant + '\'' +
                '}';
    }
}



