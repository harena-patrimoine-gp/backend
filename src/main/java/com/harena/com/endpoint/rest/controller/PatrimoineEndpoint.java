package com.harena.com.endpoint.rest.controller;

import com.harena.com.file.BucketComponent;

import com.harena.com.model.request.ArgentRequest;
import com.harena.com.model.request.FluxArgentRequest;
import com.harena.com.model.request.MaterielRequest;
import com.harena.com.service.PatrimoineServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.Argent;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.Materiel;
import school.hei.patrimoine.modele.possession.Possession;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import java.util.Set;

@RestController
@RequestMapping("/patrimoines")
@AllArgsConstructor
public class PatrimoineEndpoint {
    private final BucketComponent bucketComponent;
    private final PatrimoineServices services;

    @GetMapping("")
    public List<Patrimoine> getAll() throws IOException {
        return services.getAllPatrimoine();
    }


    @PutMapping("")
    public Patrimoine createUpdate(@RequestBody com.harena.com.model.Patrimoine patrimoine,@RequestParam String email) throws IOException {
        Patrimoine patrimoineToSave=new Patrimoine(patrimoine.getNom(),new Personne(email),patrimoine.getT(),Set.of());

        return services.create(patrimoineToSave,email);
    }

    @GetMapping("/patrimoine")
    public Patrimoine getPatrimoineByDate(@RequestParam String email, @RequestParam LocalDate date) throws IOException {
      return services.findPatrimoine(email).projectionFuture(date);
    }


    @GetMapping("/{nom_patrimoine}/graphe")
    public ResponseEntity<byte[]> getPatrimoineFuture(
            @PathVariable String nom_patrimoine,
            @RequestParam String email,
            @RequestParam LocalDate debut,
            @RequestParam LocalDate fin) throws IOException {
        if (debut == null) {
            LocalDate newDebut = LocalDate.now();
            LocalDate newFin = newDebut.plusDays(1);
            File file = services.getPatrimoineFuture(nom_patrimoine,email, newDebut, newFin);
            byte[] bytes = Files.readAllBytes(file.toPath());
            return new ResponseEntity<>(bytes, HttpStatus.OK);
        }
        File file =  services.getPatrimoineFuture(nom_patrimoine, email,debut, fin);
        return new ResponseEntity<>(Files.readAllBytes(file.toPath()), HttpStatus.OK);
    }

    @GetMapping("/{nom_patrimoine}/possessions")
    public Set<Possession> getAllPossessionByPatrimoine(@PathVariable String nom_patrimoine,@RequestParam String email) throws IOException {
        return services.getPossessionByPatrimoine(nom_patrimoine,email);
    }

    @PutMapping("/{nom_patrimoine}/possessions/materiel")
    public Set<Possession> crupdateMateriel(
            @PathVariable String nom_patrimoine,
            @RequestBody MaterielRequest materielRequest,
            @RequestParam String email
    ) throws IOException {
        Set<Possession> possessions=new HashSet<>();
        Devise devise =stringToDevise(materielRequest.getDevise());
    possessions.add(new Materiel(materielRequest.getNom()
    ,materielRequest.getT(),materielRequest.getValeurComptable(),materielRequest.getDateAcquisition(),materielRequest.getTauxDAppreciationAnuelle(),devise
    ));
    return services.crupdatePossessionByPatrimoine("patrimoine",possessions,email);
    }

    @PutMapping("/{nom_patrimoine}/possessions/argent")
    public Set<Possession> crupdateArgent(
            @PathVariable String nom_patrimoine,
            @RequestBody ArgentRequest argentRequest,
            @RequestParam String email
    ) throws IOException {
        Set<Possession> possessions = new HashSet<>();

        Devise devise=stringToDevise(argentRequest.getDevise());
        possessions.add(new Argent(
                argentRequest.getNom(),
                argentRequest.getDateOuverture(),
                argentRequest.getT(),
                argentRequest.getValeurComptable(),
                devise
        ));

        return services.crupdatePossessionByPatrimoine(nom_patrimoine, possessions, email);
    }
    private Devise stringToDevise(String devise){
       return  switch (devise) {
            case "MGA" -> Devise.MGA;
            case "EUR" -> Devise.EUR;
            case "CAD" -> Devise.CAD;
            default -> throw new IllegalArgumentException("Devise non prise en charge: " + devise);
        };

    }

    @PutMapping("/possessions/fluxArgent")
    public FluxArgent crupdateFluxArgent(
            @RequestBody FluxArgentRequest fluxArgentRequest,
            @RequestParam String argent ,
            @RequestParam String email
    ) throws IOException {

        return services.saveFluxArgent(email,argent,fluxArgentRequest);

    }

    @DeleteMapping("/possessions/{nom_possession}")
    public String deletePatrimoine(@PathVariable String nom_possession,
     @RequestParam String email
    ) throws IOException {
        return services.deletePossession(nom_possession,email);

    }

    @GetMapping("/file")
    public File getFile() throws IOException {
      return bucketComponent.download("patrimoine_list.txt");
    }
}
