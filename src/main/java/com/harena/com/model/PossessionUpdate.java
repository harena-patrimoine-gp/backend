package com.harena.com.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PossessionUpdate {
    private String nom;
    private double valeurComptable;
    private LocalDate dateAcquision;
}
