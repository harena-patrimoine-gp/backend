package com.harena.com.endpoint.rest.controller;

import com.harena.com.file.BucketComponent;

import com.harena.com.model.exception.BadRequestException;
import com.harena.com.service.PatrimoineServices;
import com.harena.com.service.utils.SerializationFunctions;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.possession.Argent;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.Materiel;
import school.hei.patrimoine.modele.possession.Possession;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

import java.util.Set;

@RestController
@RequestMapping("/patrimoines")
@AllArgsConstructor
public class PatrimoineEndpoint {
    private final SerializationFunctions serializationFunctions;
    private final BucketComponent bucketComponent;
    private final PatrimoineServices services;

    @GetMapping("")
    public List<Patrimoine> getAll() throws IOException {
        return services.getAllPatrimoine();
    }


    @PutMapping("")
    public Patrimoine createUpdate(@RequestBody com.harena.com.model.Patrimoine patrimoine,@RequestParam String email) throws IOException {
        Patrimoine patrimoineToSave=new Patrimoine(patrimoine.getNom(),patrimoine.getPossesseur(),patrimoine.getT(),Set.of());

        return services.create(patrimoineToSave,email);
    }

    @GetMapping("/{nom_patrimoine}")
    public Patrimoine getPatrimoineByName(@PathVariable String nom_patrimoine,@RequestParam String email) throws IOException {
      return services.findPatrimoineByName(nom_patrimoine,email);
    }


    @GetMapping("/{nom_patrimoine}/graphe")
    public ResponseEntity<byte[]> getPatrimoineFuture(
            @PathVariable String nom_patrimoine,
            @RequestParam LocalDate debut,
            @RequestParam LocalDate fin) throws IOException {
        if (debut == null) {
            LocalDate newDebut = LocalDate.now();
            LocalDate newFin = newDebut.plusDays(1);
            File file = services.getPatrimoineFuture(nom_patrimoine, newDebut, newFin);
            byte[] bytes = Files.readAllBytes(file.toPath());
            return new ResponseEntity<>(bytes, HttpStatus.OK);
        }
        File file =  services.getPatrimoineFuture(nom_patrimoine, debut, fin);
        return new ResponseEntity<>(Files.readAllBytes(file.toPath()), HttpStatus.OK);
    }

    @GetMapping("/{nom_patrimoine}/possessions")
    public Set<Possession> getAllPossessionByPatrimoine(@PathVariable String nom_patrimoine,@RequestParam String email) throws IOException {
        return services.getPossessionByPatrimoine(nom_patrimoine,email);
    }

    @PutMapping("/{nom_patrimoine}/possessions/materiel")
    public Set<Possession> crupdateMateriel(
            @PathVariable String nom_patrimoine,
            @RequestBody Set<Materiel> possessions,
            @RequestParam String email
    ) throws IOException {
        return services.crupdatePossessionByPatrimoine(nom_patrimoine, possessions,email);
    }

    @PutMapping("/{nom_patrimoine}/possessions/argent")
    public Set<Possession> crupdateArgent(
            @PathVariable String nom_patrimoine,
            @RequestBody Set<Argent> possessions,
            @RequestParam String email
    ) throws IOException {
        return services.crupdatePossessionByPatrimoine(nom_patrimoine, possessions,email);
    }

    @PutMapping("/{nom_patrimoine}/possessions/fluxArgent")
    public Set<Possession> crupdateFluxArgent(
            @PathVariable String nom_patrimoine,
            @RequestBody Set<FluxArgent> possessions,
            @RequestParam String email
    ) throws IOException {
        return services.crupdatePossessionByPatrimoine(nom_patrimoine, possessions,email);
    }

    @DeleteMapping("/{nom_patrimoine}/possessions/{nom_possession}")
    public String deletePatrimoine(@PathVariable String nom_patrimoine,@PathVariable String nom_possession,
     @RequestParam String email
    ) throws IOException {
        return services.deletePossession(nom_patrimoine,nom_possession,email);

    }

    @GetMapping("/file")
    public File getFile() throws IOException {
      return bucketComponent.download("patrimoine_list.txt");
    }
}
