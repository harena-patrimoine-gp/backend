package com.harena.com.service;

import com.harena.com.file.BucketComponent;
import com.harena.com.model.exception.InternalServerErrorException;

import com.harena.com.service.utils.SerializationFunctions;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import school.hei.patrimoine.modele.EvolutionPatrimoine;
import school.hei.patrimoine.modele.Patrimoine;

import school.hei.patrimoine.modele.possession.Possession;

import school.hei.patrimoine.visualisation.xchart.GrapheurEvolutionPatrimoine;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class PatrimoineServices {
    private final BucketComponent bucketComponent;
    private final SerializationFunctions functions;
    private final String patrimoineListFile = "patrimoine_list.txt";
    private final String extensionFile = ".txt";

    public Patrimoine create(Patrimoine patrimoine) throws IOException {
        try {
            String list = new String(Files.readAllBytes(bucketComponent.download(patrimoineListFile).toPath()));
            String updatedList = list + patrimoine.nom() + ";";
            bucketComponent.upload(functions.writeToTxt(updatedList, patrimoineListFile), patrimoineListFile);
            File createdFile = functions.serialize(patrimoine);
            bucketComponent.upload(createdFile, patrimoine.nom() + extensionFile);
            return patrimoine;

        } catch (InternalServerErrorException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Patrimoine> getAllPatrimoine() throws IOException {
        List<Patrimoine> patrimoines = new ArrayList<>();
        File list = bucketComponent.download(patrimoineListFile);
        String decodedList = new String(Files.readAllBytes(list.toPath()));
        List<String> listPatrimoineFiles = Arrays.asList(decodedList.split(";"));
        for (String listPatrimoineFile : listPatrimoineFiles) {
            File file = bucketComponent.download(listPatrimoineFile + extensionFile);
            patrimoines.add(functions.decodeFile(file));
        }
        return patrimoines;
    }

    public File getPatrimoineFuture(String nom_patrimoine, LocalDate debut, LocalDate fin) throws IOException {
        File file = bucketComponent.download(nom_patrimoine + extensionFile);
        Patrimoine actual = functions.decodeFile(file);
        Patrimoine futurPatrimoine = actual.projectionFuture(actual.t());
        EvolutionPatrimoine evolutionPatrimoine = new EvolutionPatrimoine(futurPatrimoine.nom() + "evolution", futurPatrimoine, debut, fin);
        GrapheurEvolutionPatrimoine grapheur = new GrapheurEvolutionPatrimoine();
        return grapheur.apply(evolutionPatrimoine);
    }

    public Set<Possession> getPossessionByPatrimoine(String nom_patrimoine) throws IOException {
        File file = bucketComponent.download(nom_patrimoine + extensionFile);
        Patrimoine actual = functions.decodeFile(file);
        return actual.possessions();
    }

    public <T extends Possession> Set<Possession> crupdatePossessionByPatrimoine(String nom_patrimoine, Set<T> possessions) throws IOException {
        File file = bucketComponent.download(nom_patrimoine + extensionFile);
        Patrimoine actual = functions.decodeFile(file);
        Set<Possession> actualPossessions = getPossessionByPatrimoine(nom_patrimoine);
        Set<Possession> possessionSet = new HashSet<>();
        possessionSet.addAll(actualPossessions);
        possessionSet.addAll(possessions);
        Patrimoine updated = new Patrimoine(nom_patrimoine, actual.possesseur(), actual.t(), possessionSet);
        bucketComponent.upload(functions.serialize(updated), actual.nom() + extensionFile);
        return possessionSet;
    }
}
