package com.harena.com.repositories.model;

import lombok.Data;
import lombok.Getter;
import school.hei.patrimoine.modele.possession.Argent;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.Materiel;
@Getter
public class PossessionType {
    private  Argent argent;
    private  Materiel materiel;
    private  FluxArgent fluxArgent;

    public PossessionType(Argent argent) {
        this.argent = argent;
    }

    public PossessionType(Materiel materiel) {
        this.materiel = materiel;
    }

    public PossessionType(FluxArgent fluxArgent) {
        this.fluxArgent = fluxArgent;
    }
}
