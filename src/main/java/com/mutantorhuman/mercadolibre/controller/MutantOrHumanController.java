package com.mutantorhuman.mercadolibre.controller;
import com.mutantorhuman.mercadolibre.model.DnaRequest;
import com.mutantorhuman.mercadolibre.model.EstadisticasResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mutantorhuman.mercadolibre.service.IMutantOrHumanService;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class MutantOrHumanController {

    @Autowired
    private IMutantOrHumanService mutantOrHumanService;

    @PostMapping("/mutant")
    public ResponseEntity<Void> isMutant(@RequestBody @Valid DnaRequest dnaRequest) {
        try {
            mutantOrHumanService.isMutant(dnaRequest);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
         catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }

    @GetMapping("/stats")
    public EstadisticasResponse verEstadisticas() {
        return mutantOrHumanService.verEstadisticas();
    }




}



