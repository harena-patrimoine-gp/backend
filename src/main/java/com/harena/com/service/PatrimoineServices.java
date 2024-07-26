package com.harena.com.service;

import com.harena.com.file.BucketComponent;
import com.harena.com.model.exception.InternalServerErrorException;
import com.harena.com.service.utils.SerializationFunctions;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.possession.Possession;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
@AllArgsConstructor
public class PatrimoineServices {
    private final BucketComponent bucketComponent;
    private final SerializationFunctions functions;


    public Patrimoine updatePatrimoine(Patrimoine actual, Patrimoine change) {
        Patrimoine update = actual.projectionFuture(change.t());
        Set<Possession> updatedPossession = new HashSet<>(update.possessions());
        updatedPossession.addAll(change.possessions());
        Patrimoine patrimoine = new Patrimoine(actual.nom(),
                actual.possesseur(),
                change.t(),
                updatedPossession);
        return patrimoine;
    }

    public Patrimoine create(Patrimoine patrimoine) throws IOException {
        try {
        String list =  new String(Files.readAllBytes(bucketComponent.download("patrimoine_list.txt").toPath()));
        String updatedList = list+patrimoine.nom()+";";
        bucketComponent.upload(functions.writeToTxt(updatedList,"patrimoine_list.txt"),"patrimoine_list.txt");
        File createdFile = functions.serialize(patrimoine);
        bucketComponent.upload(createdFile, patrimoine.nom() + ".txt");
        return patrimoine;

        } catch (InternalServerErrorException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Patrimoine> getAllPatrimoine() throws IOException {
        List<Patrimoine> patrimoines = new ArrayList<>();
        File list = bucketComponent.download("patrimoine_list.txt");
        String decodedList = new String(Files.readAllBytes(list.toPath()));
        List<String> listPatrimoineFiles = Arrays.asList(decodedList.split(";"));
        for (String listPatrimoineFile : listPatrimoineFiles){
            File file = bucketComponent.download(listPatrimoineFile+".txt");
            patrimoines.add(functions.decodeFile(file));
        }
        return patrimoines;
    }
}
