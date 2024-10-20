package com.harena.com.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@EqualsAndHashCode(callSuper = true)
@Getter
@NoArgsConstructor
public class ArgentRequest extends PossessionRequest {
    private LocalDate dateOuverture;


    public ArgentRequest(String nom, LocalDate dateOuverture,LocalDate t, int valeurComptable, String devise) {
        super(nom, t, valeurComptable, devise);
        this.dateOuverture=dateOuverture;
    }
}
