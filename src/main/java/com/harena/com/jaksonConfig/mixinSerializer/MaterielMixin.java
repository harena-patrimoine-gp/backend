package com.harena.com.jaksonConfig.mixinSerializer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import school.hei.patrimoine.modele.Devise;

import java.time.LocalDate;

public abstract class MaterielMixin {
    @JsonCreator
    public MaterielMixin(
            @JsonProperty("nom") String nom,
            @JsonProperty("t") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate t,
            @JsonProperty("valeurComptable") int valeurComptable,
            @JsonProperty("dateAcquisition") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate dateAcquisition,
            @JsonProperty("tauxDAppreciationAnnuelle") double tauxDAppreciationAnnuelle,
            @JsonProperty("devise") Devise devise) {}
}
