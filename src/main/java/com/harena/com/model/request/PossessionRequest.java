package com.harena.com.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Getter
@NoArgsConstructor
public class PossessionRequest {
    private String nom;
    private LocalDate t;
    private int valeurComptable;
    private String devise;

    public PossessionRequest(String nom, LocalDate t, int valeurComptable, String devise) {
        this.nom = nom;
        this.t = t;
        this.valeurComptable = valeurComptable;
        this.devise = devise;
    }


}
