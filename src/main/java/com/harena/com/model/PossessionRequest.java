package com.harena.com.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
public class PossessionRequest {
    private String nom;
    private LocalDate t;
    private String valeurComptable;
    private String devise;

    public PossessionRequest(String nom, LocalDate t, String valeurComptable, String devise) {
        this.nom = nom;
        this.t = t;
        this.valeurComptable = valeurComptable;
        this.devise = devise;
    }
}
