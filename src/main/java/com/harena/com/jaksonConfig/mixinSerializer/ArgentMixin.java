package com.harena.com.jaksonConfig.mixinSerializer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import school.hei.patrimoine.modele.Devise;

import java.time.LocalDate;

public abstract class ArgentMixin {
    @JsonCreator
    public ArgentMixin(
            @JsonProperty("nom") String nom,
            @JsonProperty("dateOuverture") LocalDate dateOuverture,
            @JsonProperty("t") LocalDate t,
            @JsonProperty("valeurComptable") int valeurComptable,
            @JsonProperty("devise") Devise devise
    ) {
    }
}
