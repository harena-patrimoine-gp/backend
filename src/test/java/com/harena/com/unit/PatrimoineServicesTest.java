package com.harena.com.unit;

import com.harena.com.service.PatrimoineServices;
import com.harena.com.file.BucketComponent;
import com.harena.com.service.utils.SerializationFunctions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.Possession;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class PatrimoineServicesTest {

    private PatrimoineServices services;
    private BucketComponent bucketComponent;
    private SerializationFunctions functions;

    @BeforeEach
    public void setup() {
        bucketComponent = Mockito.mock(BucketComponent.class);
        functions = Mockito.mock(SerializationFunctions.class);
        services = new PatrimoineServices(bucketComponent, functions);
    }

    @Test
    public void testCreatePatrimoine() throws Exception {
        Personne possesseur = new Personne("nomTest");
        Set<Possession> possessions = new HashSet<>();
        Patrimoine patrimoine = new Patrimoine("testNom", possesseur, LocalDate.now(), possessions);

        File tempFile = Files.createTempFile("test", ".txt").toFile();
        Files.write(tempFile.toPath(), "test content".getBytes());

        Mockito.when(bucketComponent.download(eq("patrimoine_list.txt"))).thenReturn(tempFile);
        Mockito.when(functions.writeToTxt(any(String.class), eq("patrimoine_list.txt"))).thenReturn(tempFile);
        Mockito.when(functions.serialize(any(Patrimoine.class))).thenReturn(tempFile);
        Mockito.when(bucketComponent.upload(any(File.class), eq("patrimoine_list.txt"))).thenReturn(null);
        Mockito.when(bucketComponent.upload(any(File.class), eq("testNom.txt"))).thenReturn(null);

        Patrimoine result = services.create(patrimoine);

        assertEquals(patrimoine.nom(), result.nom());
    }

    @Test
    public void testCreatePatrimoineWithNull() {
        assertThrows(NullPointerException.class, () -> {
            services.create(null);
        });
    }

    @Test
    public void testGetAllPatrimoine() throws Exception {
        Personne possesseur = new Personne("nomTest");
        Set<Possession> possessions = new HashSet<>();
        Patrimoine patrimoine = new Patrimoine("testNom", possesseur, LocalDate.now(), possessions);

        File tempFile = Files.createTempFile("test", ".txt").toFile();
        Files.write(tempFile.toPath(), "test content".getBytes());

        Mockito.when(bucketComponent.download(eq("patrimoine_list.txt"))).thenReturn(tempFile);
        Mockito.when(functions.decodeFile(tempFile)).thenReturn(patrimoine);

        assertEquals(1, services.getAllPatrimoine().size());
    }
}
