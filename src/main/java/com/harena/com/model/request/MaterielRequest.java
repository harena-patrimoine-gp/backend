package com.harena.com.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import school.hei.patrimoine.modele.possession.Materiel;

import java.time.LocalDate;
@EqualsAndHashCode(callSuper = true)
@Getter
@NoArgsConstructor
public class MaterielRequest extends PossessionRequest{
    private  LocalDate dateAcquisition;
    private  int tauxDAppreciationAnuelle;
    public MaterielRequest(String nom, LocalDate date,LocalDate dateAquisition,int valeurComptable,int tauxDApreciationAnuelle,String devise){
        super(nom,date,valeurComptable,devise);
        this.dateAcquisition=dateAquisition;
        this.tauxDAppreciationAnuelle=tauxDApreciationAnuelle;

    }
}
