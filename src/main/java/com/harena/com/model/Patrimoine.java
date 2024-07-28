package com.harena.com.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import school.hei.patrimoine.modele.Personne;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class Patrimoine {
    private  String nom;
    private Personne personne;
    private LocalDate t;
}
