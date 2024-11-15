package com.mutantorhuman.mercadolibre.service;

import com.mutantorhuman.mercadolibre.model.Dna;
import com.mutantorhuman.mercadolibre.model.DnaRequest;
import com.mutantorhuman.mercadolibre.model.EstadisticasResponse;
import com.mutantorhuman.mercadolibre.repository.IMutantOrHumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;


@Service
public class MutantOrHumanService implements IMutantOrHumanService {

    @Autowired
    private IMutantOrHumanRepository mutantOrHumanRepository;

    @Override
    public boolean isMutant(DnaRequest dnaRequest) {

        System.out.println(dnaRequest.toString());

        Dna dna = new Dna();
        dna.setDna(Arrays.toString(dnaRequest.getDna()));

        char[][] matrizDna = validarRecorrerMatriz (dnaRequest.getDna());

        if (matrizDna == null) {
            throw new IllegalArgumentException("Error en el Dna, validaciòn incorrecta. La matriz debe ser cuadrada, y no debe haber otros letras que no sean la A, G, T y C.");
        }
        else {
            int contadorSecuencia4LetrasHorizontales   = busquedaHorizontal(dnaRequest.getDna(),matrizDna);
            int contadorSecuencia4LetrasVerticales   = busquedaVertical(dnaRequest.getDna(),matrizDna);
            int contadorSecuencia4LetrasDiagonalIzqDer   = busquedaDiagonalIzquierdaADerecha(dnaRequest.getDna(),matrizDna);
            int contadorSecuencia4LetrasDiagonalDerIzq = busquedaDiagonalDerechaIzquierda(dnaRequest.getDna(),matrizDna);



            int contadorTotalDeSecuencia4letras = contadorSecuencia4LetrasHorizontales+ contadorSecuencia4LetrasVerticales+ contadorSecuencia4LetrasDiagonalIzqDer+contadorSecuencia4LetrasDiagonalDerIzq;

            if(contadorTotalDeSecuencia4letras > 1) {
                dna.setIsMutant("true");
                mutantOrHumanRepository.save(dna);
                return true;
            } else {
                dna.setIsMutant("false");
                mutantOrHumanRepository.save(dna);
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No es mutante");
            }
        }



    }

    @Override
    public EstadisticasResponse verEstadisticas() {
        List<Dna> dnaRegisterList = mutantOrHumanRepository.findAll();
        System.out.println(dnaRegisterList);
        EstadisticasResponse estadisticasResponse = new EstadisticasResponse();

        int countMutant = 0;
        int countHuman = 0;

        for (Dna dna : dnaRegisterList) {
            if (dna.getIsMutant().equals("true")) {
                countMutant++;
            } else {
                countHuman++;
            }
        }

        estadisticasResponse.setCountMutantDna(countMutant);
        estadisticasResponse.setCountHumanDna(countHuman);

        if (countHuman == 0) {
            if (countMutant == 0) {
                estadisticasResponse.setRadio(0.0);
            } else {
                estadisticasResponse.setRadio(null);
            }

        } else {
            estadisticasResponse.setRadio((double) countMutant /countHuman);
        }



        return estadisticasResponse;
    }


    public char[][] validarRecorrerMatriz (String[] dna) {
        int matrizLength = dna.length;
        char[][] dnaMatriz = new char[matrizLength][matrizLength];

        for (int i=0; i < dna.length; i++) {
            if (dna[i].length() != matrizLength) {
                return null;
            }
        }

        for (int f=0; f < dna.length ;f++) {
            for (int c=0; c < dna.length ;c++) {
                char letra = dna[f].charAt(c);
                if(letra == 'A' || letra == 'G' || letra == 'T' || letra == 'C') {
                    dnaMatriz[f][c] = letra;
                } else {
                    return null;
                }
            }
        }
        return dnaMatriz;
    }

    public int busquedaHorizontal (String[] dna, char[][] dnaMatriz) {
        int contadorTotal4LetrasSeguidasHorizontal = 0 ;
        for (int f=0; f < dna.length ;f++) {
            int contadorLetraAnteriorIgualLetraActual = 1;
            for (int c=1; c < dna.length ;c++) {
                char letraActual = Character.toUpperCase(dnaMatriz[f][c]) ;
                char letraAnterior = Character.toUpperCase(dnaMatriz[f][c-1]);
                if(letraAnterior == letraActual) {
                    contadorLetraAnteriorIgualLetraActual++;
                }
                else {
                    contadorLetraAnteriorIgualLetraActual = 1;
                }

                if(contadorLetraAnteriorIgualLetraActual == 4) {
                    contadorLetraAnteriorIgualLetraActual = 3;
                    contadorTotal4LetrasSeguidasHorizontal++;
                }
            }
        }
        return contadorTotal4LetrasSeguidasHorizontal;
    }


    public int busquedaVertical (String[] dna, char[][] dnaMatriz) {
        int contadorTotal4LetrasSeguidasVertical = 0 ;
        for (int c=0; c < dna.length ;c++) {
            int contadorLetraAnteriorIgualLetraActual = 1;
            for (int f=1; f < dna.length ; f++) {
                char letraActual = Character.toUpperCase(dnaMatriz[f][c]) ;
                char letraAnterior = Character.toUpperCase(dnaMatriz[f-1][c]);
                if(letraAnterior == letraActual) {
                    contadorLetraAnteriorIgualLetraActual++;
                }
                else {
                    contadorLetraAnteriorIgualLetraActual = 1;
                }

                if(contadorLetraAnteriorIgualLetraActual== 4) {
                    contadorLetraAnteriorIgualLetraActual = 3;
                    contadorTotal4LetrasSeguidasVertical++;
                }
            }

        }
        return contadorTotal4LetrasSeguidasVertical;
    }


    public int busquedaDiagonalIzquierdaADerecha (String[] dna, char[][] dnaMatriz) {
        int contadorTotal4LetrasSeguidasDiagonalIzquierdaDerecha = 0 ;
        for (int f=0; f < dna.length - 3 ;f++) {
            int contadorLetraAnteriorIgualLetraActual = 1;
            if(f == 0) {
                for (int c=0; c < dna.length - 3 ; c++) {
                    contadorLetraAnteriorIgualLetraActual = 1;
                    for (int d=1; d < dna.length - c ; d++) {
                        char letraAnterior = Character.toUpperCase(dnaMatriz[d-1][(d+c)-1]);
                        char letraActual = Character.toUpperCase(dnaMatriz[d][d+c]);
                        if(letraAnterior==letraActual) {
                            contadorLetraAnteriorIgualLetraActual++;
                        }
                        else {
                            contadorLetraAnteriorIgualLetraActual = 1;
                        }

                        if(contadorLetraAnteriorIgualLetraActual == 4) {
                            contadorLetraAnteriorIgualLetraActual=3;
                            contadorTotal4LetrasSeguidasDiagonalIzquierdaDerecha++;
                        }
                    }
                }
            }
            else {
                contadorLetraAnteriorIgualLetraActual = 1;
                for (int c=0; c < 1 ; c++) {
                    for (int d=1; d < dna.length - f ; d++) {
                        char letraAnterior = Character.toUpperCase(dnaMatriz[(d+f)-1][d-1]);
                        char letraActual = Character.toUpperCase(dnaMatriz[d+f][d]);
                        if(letraAnterior==letraActual) {
                            contadorLetraAnteriorIgualLetraActual++;
                        }
                        else {
                            contadorLetraAnteriorIgualLetraActual = 1;
                        }

                        if(contadorLetraAnteriorIgualLetraActual == 4) {
                            contadorLetraAnteriorIgualLetraActual=3;
                            contadorTotal4LetrasSeguidasDiagonalIzquierdaDerecha++;
                        }
                    }
                }
            }
        }
        return contadorTotal4LetrasSeguidasDiagonalIzquierdaDerecha;
    }



    public int busquedaDiagonalDerechaIzquierda (String[] dna, char[][] dnaMatriz) {
        int contadorTotal4LetrasSeguidasDiagonalDerechaIzquierda= 0 ;
        for (int f=dna.length - 1; f > 2 ;f--) {
            int contadorLetraAnteriorIgualLetraActual = 1;

            if(f == dna.length - 1) {
                for (int c=0; c < dna.length - 3 ; c++) {
                    contadorLetraAnteriorIgualLetraActual = 1;
                    for (int d=1; d < dna.length - c ; d++) {
                        char letraAnterior = Character.toUpperCase(dnaMatriz[(f-d)+1][(c+d)-1]);
                        char letraActual = Character.toUpperCase(dnaMatriz[f-d][c+d]);
                        if(letraAnterior==letraActual) {
                            contadorLetraAnteriorIgualLetraActual++;
                        }
                        else {
                            contadorLetraAnteriorIgualLetraActual = 1;
                        }

                        if(contadorLetraAnteriorIgualLetraActual == 4) {
                            contadorLetraAnteriorIgualLetraActual= 3;
                            contadorTotal4LetrasSeguidasDiagonalDerechaIzquierda++;
                        }
                    }
                }
            }
            else {
                System.out.println(f);
                for (int c= 0; c == 0 ; c++) {

                    for (int d=1; d < f + 1 ; d++) {
                        char letraAnterior = Character.toUpperCase(dnaMatriz[(f-d)+1][d-1]);
                        char letraActual = Character.toUpperCase(dnaMatriz[f-d][d]);
                        if(letraAnterior==letraActual) {
                            contadorLetraAnteriorIgualLetraActual++;
                        }
                        else {
                            contadorLetraAnteriorIgualLetraActual = 1;
                        }

                        if(contadorLetraAnteriorIgualLetraActual == 4) {
                            contadorLetraAnteriorIgualLetraActual= 3;
                            contadorTotal4LetrasSeguidasDiagonalDerechaIzquierda++;
                        }
                    }
                }
            }

        }
        return contadorTotal4LetrasSeguidasDiagonalDerechaIzquierda;
    }
}


