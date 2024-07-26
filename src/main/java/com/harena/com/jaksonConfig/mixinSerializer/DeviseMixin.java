package com.harena.com.jaksonConfig.mixinSerializer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public abstract class DeviseMixin {
    @JsonCreator
    public DeviseMixin(
            @JsonProperty("nom") String nom,
            @JsonProperty("valeurEnAriary") int valeurEnAriary,
            @JsonProperty("t") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate t,
            @JsonProperty("tauxDappréciationAnnuel") double tauxDappréciationAnnuel) {}
}
