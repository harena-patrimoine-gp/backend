package com.harena.com.model.request;

import lombok.*;
import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.possession.Argent;

import java.time.LocalDate;
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class FluxArgentRequest extends PossessionRequest{

    private LocalDate debut;
    private LocalDate fin;
    private int fluxMensuel;
    private int dateOperation;

    public FluxArgentRequest(String nom,LocalDate debut,LocalDate fin, int valeurComptable, int fluxMensuel,int dateOperation,String devise) {
        super(nom, null, valeurComptable, devise);
        this.debut=debut;
        this.fin=fin;
        this.fluxMensuel=fluxMensuel;
        this.dateOperation=dateOperation;
    }
}


