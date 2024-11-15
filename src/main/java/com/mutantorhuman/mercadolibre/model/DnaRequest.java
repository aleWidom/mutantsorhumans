package com.mutantorhuman.mercadolibre.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;


@Setter
@Getter
public class DnaRequest {

    @NotNull(message = "El array no puede ser nulo")
    @NotEmpty(message = "El array no puede estar vacío")
    @Size(min = 4, message = "El Array debe ser minimo 4 de longitud")
    String[] dna;

    public DnaRequest() {
    }

    public DnaRequest(String[] dna) {
        this.dna = dna;
    }

    @Override
    public String toString() {
        return "DnaRequest{" +
                "dna=" + Arrays.toString(dna) +
                '}';
    }
}
