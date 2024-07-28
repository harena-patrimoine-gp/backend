package com.harena.com.unit.patrimoineTest;


import com.harena.com.service.utils.SerializationFunctions;

import org.junit.jupiter.api.Test;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.Possession;


import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;


public class SerialisationFunctionTest {
    SerializationFunctions serializationFunctions = new SerializationFunctions();
    @Test
    public void test_string_to_txt() throws IOException {
        File file = serializationFunctions.writeToTxt("this is the content" , "try.txt");
        String contentRead = new String(Files.readAllBytes(file.toPath()));

        assertEquals("this is the content" , contentRead);
    }

    @Test
    public void test_serialisation_of_patrimoine_and_then_decode_it() throws IOException {
        Set<Possession> possessions = new HashSet<>();
        Personne tsiory = new Personne("tsiory");
        Patrimoine tsioryPatrimoine = new Patrimoine("etudiant" , tsiory, LocalDate.now(),possessions);

        File encodedPatrimoine = serializationFunctions.serialize(tsioryPatrimoine);
        Patrimoine decoded = serializationFunctions.decodeFile(encodedPatrimoine);

        assertEquals(tsioryPatrimoine , decoded);
    }

}
