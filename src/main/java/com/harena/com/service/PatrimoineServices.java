package com.harena.com.service;

import com.harena.com.file.BucketComponent;
import com.harena.com.file.FileHash;
import com.harena.com.model.exception.BadRequestException;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatrimoineServices {
    private final BucketComponent bucketComponent;
    private final SerializationFunctions<Patrimoine> functions;

    private final String patrimoineListFile = "patrimoine_list.txt";
    private final String extensionFile = ".txt";

    public Patrimoine create(Patrimoine patrimoine,String userEmail) throws IOException {
        try {
          /*  File patrimoineList = bucketComponent.download(patrimoineListFile);

            String list = new String(Files.readAllBytes(patrimoineList.toPath()));
            String updatedList = list + patrimoine.nom() + ";";
            File updatedPatrimoineList = functions.writeToTxt(updatedList, patrimoineListFile);*/
          //  bucketComponent.upload(updatedPatrimoineList, patrimoineListFile);
            File createdFile = functions.serialize(patrimoine,"patrimoine");
            bucketComponent.upload(createdFile, userEmail+"/patrimoine" + extensionFile);
            //Files.deleteIfExists(patrimoineList.toPath());

            return patrimoine;

        } catch (InternalServerErrorException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Patrimoine> getAllPatrimoine() throws IOException {
        List<Patrimoine> patrimoines = new ArrayList<>();
        File listPatrimoine = bucketComponent.download(patrimoineListFile);
        String decodedList = new String(Files.readAllBytes(listPatrimoine.toPath()));
        List<String> listPatrimoineFiles = Arrays.asList(decodedList.split(";"));
        for (String listPatrimoineFile : listPatrimoineFiles) {
            File file = bucketComponent.download(listPatrimoineFile + extensionFile);
            patrimoines.add(functions.decodeFile(file));
            Files.delete(file.toPath());
        }
        Files.deleteIfExists(listPatrimoine.toPath());
        return patrimoines;
    }

    public File getPatrimoineFuture(String nom_patrimoine,String userEmail, LocalDate debut, LocalDate fin) throws IOException {
        File file = bucketComponent.download(userEmail+"/"+nom_patrimoine + extensionFile);
        Patrimoine actual = functions.decodeFile(file);
        Patrimoine futurPatrimoine = actual.projectionFuture(fin);
        EvolutionPatrimoine evolutionPatrimoine = new EvolutionPatrimoine(futurPatrimoine.nom() + "evolution", futurPatrimoine, debut, fin);
        GrapheurEvolutionPatrimoine grapheur = new GrapheurEvolutionPatrimoine();
        Files.deleteIfExists(file.toPath());
        return grapheur.apply(evolutionPatrimoine);
    }

    public Set<Possession> getPossessionByPatrimoine(String nom_patrimoine,String userEmail) throws IOException {
        File file = bucketComponent.download(userEmail+"/"+nom_patrimoine + extensionFile);
        Patrimoine actual = functions.decodeFile(file);
        Files.delete(file.toPath());
        return actual.possessions();
    }

    public <T extends Possession> Set<Possession> crupdatePossessionByPatrimoine(String nom_patrimoine, Set<T> possessions,String userEmail) throws IOException {
        File file = bucketComponent.download(userEmail+"/"+nom_patrimoine + extensionFile);
        Patrimoine actual = functions.decodeFile(file);
        Set<Possession> actualPossessions = getPossessionByPatrimoine(nom_patrimoine,userEmail);
        Set<Possession> possessionSet = new HashSet<>();
        possessionSet.addAll(actualPossessions);
        possessionSet.addAll(possessions);
        Patrimoine updated = new Patrimoine(nom_patrimoine, actual.possesseur(), actual.t(), possessionSet);
        File updatedPatrimoine = functions.serialize(updated,updated.nom());
        bucketComponent.upload(updatedPatrimoine, userEmail+"/"+nom_patrimoine+ extensionFile);
        Files.deleteIfExists(file.toPath());
        return possessionSet;
    }

    public Patrimoine findPatrimoine(String userEmail) {
        try {
            File file = bucketComponent.download(userEmail+"/patrimoine"+ extensionFile);
            Patrimoine patrimoine = functions.decodeFile(file);
            Files.deleteIfExists(file.toPath());
            return patrimoine;

        } catch (BadRequestException | IOException e) {
            throw new BadRequestException( "patrimony does not exist");
        }
    }

    public String deletePossession( String possessionName,String userEmail) throws IOException {


        Patrimoine patrimoine = findPatrimoine(userEmail);
        Set<Possession> possessions = patrimoine.possessions();
        Set<Possession> filteredPossessions = possessions.stream().filter(
                possession -> !possession.getNom().equals(possessionName)
        ).collect(Collectors.toSet());
        Patrimoine patrimoineWithDeletedPossession = new Patrimoine(
                patrimoine.nom(), patrimoine.possesseur(), patrimoine.t(), filteredPossessions
        );
        create(patrimoineWithDeletedPossession,patrimoine.nom());
        return "la possession est supprimé avec succes";

    }
}
