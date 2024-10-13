package com.harena.com.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@EqualsAndHashCode(callSuper = true)
@Data
public class ArgentRequest extends PossessionRequest{
    private LocalDate dateOuverture;

    public ArgentRequest(String nom, LocalDate t, String valeurComptable, String devise,LocalDate dateOuverture) {
        super(nom, t, valeurComptable, devise);
        this.dateOuverture=dateOuverture;
    }
}
