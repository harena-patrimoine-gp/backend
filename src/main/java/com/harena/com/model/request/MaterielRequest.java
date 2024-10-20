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
    private  double tauxDAppreciationAnuelle;
    public MaterielRequest(String nom, LocalDate t,LocalDate dateAcquisition,int valeurComptable,double tauxDAppreciationAnuelle,String devise){
        super(nom,t,valeurComptable,devise);
        this.dateAcquisition=dateAcquisition;
        this.tauxDAppreciationAnuelle=tauxDAppreciationAnuelle;

    }
}
