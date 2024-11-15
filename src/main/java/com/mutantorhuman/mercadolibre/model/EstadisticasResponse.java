package com.mutantorhuman.mercadolibre.model;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadisticasResponse {

     int countMutantDna;
     int countHumanDna;
     Double radio;

    public EstadisticasResponse() {
    }

    public EstadisticasResponse(double radio, int countHumanDna, int countMutantDna) {
        this.radio = radio;
        this.countHumanDna = countHumanDna;
        this.countMutantDna = countMutantDna;
    }
}


