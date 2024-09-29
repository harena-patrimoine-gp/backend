package com.harena.com.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import school.hei.patrimoine.modele.Personne;

import java.io.Serializable;
import java.time.LocalDate;
@Data
public class Patrimoine implements Serializable {
    private  String nom;
    private Personne possesseur;
    private LocalDate t;

}
