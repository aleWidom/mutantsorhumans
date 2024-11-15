package com.mutantorhuman.mercadolibre.repository;

import com.mutantorhuman.mercadolibre.model.Dna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMutantOrHumanRepository extends JpaRepository<Dna, String> {
}


