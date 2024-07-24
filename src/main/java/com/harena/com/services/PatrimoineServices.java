package com.harena.com.services;

import com.harena.com.file.BucketComponent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.possession.Possession;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class PatrimoineServices {
    private final BucketComponent bucketComponent;
    private final SerializationFunctions functions;

    private Patrimoine updatePatrimoine(Patrimoine actual , Patrimoine change){
        Patrimoine update = actual.projectionFuture(change.t());
        Set<Possession> updatedPossession  = new HashSet<>(update.possessions());
        updatedPossession.addAll(change.possessions());
        Patrimoine patrimoine = new Patrimoine(actual.nom(),
                actual.possesseur() ,
                change.t(),
                updatedPossession);
        return patrimoine;
    }

    public Patrimoine createUpdate (Patrimoine patrimoine) throws IOException {
        File file = bucketComponent.download(patrimoine.nom()+".txt");
        if (file.exists()){
            Patrimoine actual = functions.decodeFile(file);
            Patrimoine updated = updatePatrimoine(actual , patrimoine);
            File updatedToFile = functions.serialize(updated);
            bucketComponent.upload(updatedToFile , updated.nom()+".txt");
            return updated;
        }
        File createdFile = functions.serialize(patrimoine);
        bucketComponent.upload(createdFile , patrimoine.nom()+".txt");
        return patrimoine;
    }
}
