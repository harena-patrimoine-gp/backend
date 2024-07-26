package com.harena.com.endpoint.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harena.com.file.BucketComponent;
import com.harena.com.service.PatrimoineServices;
import com.harena.com.service.utils.SerializationFunctions;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.possession.Materiel;
import school.hei.patrimoine.modele.possession.Possession;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@AllArgsConstructor
public class PatrimoineEndpoint {
    private final SerializationFunctions serializationFunctions;
    private final BucketComponent bucketComponent;
    private final PatrimoineServices services;

    @GetMapping("/patrimoines")
    public List<Patrimoine> getAll() throws IOException {
        return services.getAllPatrimoine();
    }

    @PutMapping("/patrimoines")
    public Patrimoine createUpdate(@RequestBody Patrimoine patrimoine) throws IOException {
        return services.create(patrimoine);
    }

    @GetMapping("/patrimoines/{nom_patrimoine}")
    public Patrimoine getPatrimoineByName(@PathVariable String nom_patrimoine) throws IOException {
        File file = bucketComponent.download(nom_patrimoine+".txt");
        return serializationFunctions.decodeFile(file);
    }

    @GetMapping("/file")
    public String url() throws IOException {
        return new String(Files.readAllBytes(bucketComponent.download("patrimoine_list.txt" ).toPath()));
    }

    @GetMapping("/patrimoines/{nom_patrimoine}/graphe")
    public File getPatrimoineFuture(
            @PathVariable String nom_patrimoine,
            @RequestParam LocalDate debut,
            @RequestParam LocalDate fin) throws IOException {
         if (debut == null ) {
            LocalDate newDebut = LocalDate.now();
            LocalDate newFin = newDebut.plusDays(1);
            return services.getPatrimoineFuture(nom_patrimoine, newDebut, newFin);
        }
        return services.getPatrimoineFuture(nom_patrimoine, debut, fin);
    }

    @GetMapping("/patrimoines/{nom_patrimoine}/possessions")
    public Set<Possession> getAllPossessionByPatrimoine(@PathVariable String nom_patrimoine) throws IOException {
        return services.getPossessionByPatrimoine(nom_patrimoine);
    }

    @PutMapping("/patrimoines/{nom_patrimoine}/possessions")
    public Set<Possession> crupdatePossession(
            @PathVariable String nom_patrimoine,
            @RequestBody Materiel possessions
    ) throws IOException {
        return services.crupdatePossessionByPatrimoine(nom_patrimoine, possessions);
    }


}
