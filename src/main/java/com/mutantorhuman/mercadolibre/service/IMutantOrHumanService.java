package com.mutantorhuman.mercadolibre.service;

import com.mutantorhuman.mercadolibre.model.Dna;
import com.mutantorhuman.mercadolibre.model.DnaRequest;
import com.mutantorhuman.mercadolibre.model.EstadisticasResponse;

public interface IMutantOrHumanService {
    boolean isMutant(DnaRequest dnaRequest);

    EstadisticasResponse verEstadisticas();
}

