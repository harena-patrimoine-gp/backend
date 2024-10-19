package com.harena.com.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.possession.Argent;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FluxArgentRequest {
    private String nom;
    private LocalDate debut;
    private LocalDate fin;
    private int fluxMensuel;
    private int dateOperation;
}


