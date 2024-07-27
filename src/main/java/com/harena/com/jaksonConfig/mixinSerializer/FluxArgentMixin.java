package com.harena.com.jaksonConfig.mixinSerializer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.possession.Argent;

import java.time.LocalDate;

public abstract class FluxArgentMixin {
    @JsonCreator
    public FluxArgentMixin(
            @JsonProperty("nom") String nom,
            @JsonProperty("argent") Argent argent,
            @JsonProperty("debut") LocalDate debut,
            @JsonProperty("fin") LocalDate fin,
            @JsonProperty("fluxMensuel") int fluxMensuel,
            @JsonProperty("dateOperation") int dateOperation,
            @JsonProperty("devise") Devise devise
    ) {
    }
}
