package com.harena.com.unit.patrimoineTest;

import com.harena.com.service.PatrimoineServices;
import com.harena.com.file.BucketComponent;
import com.harena.com.service.utils.SerializationFunctions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.Materiel;
import school.hei.patrimoine.modele.possession.Possession;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PatrimoineServicesTest {
    @Mock
    private BucketComponent bucketComponent;
    @Mock
    private SerializationFunctions functions;
    @InjectMocks
    private PatrimoineServices services;
    private final String patrimoineListFileName = "patrimoine_list.txt";
    private final String extensionFile = ".txt";
    private File patrimoineFile;

    @BeforeEach
    public void setup() throws IOException {
        bucketComponent = Mockito.mock(BucketComponent.class);
        functions = Mockito.mock(SerializationFunctions.class);
        services = new PatrimoineServices(bucketComponent, functions);

        //creation de patimoine file pr simuler
        patrimoineFile = Files.createTempFile("testPatrimoine", extensionFile).toFile();
    }

    @Test
    public void testCreatePatrimoine() throws Exception {
        Personne possesseur = new Personne("nomTest");
        Set<Possession> possessions = new HashSet<>();
        Patrimoine patrimoine = new Patrimoine("testNom", possesseur, LocalDate.now(), possessions);

        File patrimoineListFile = new File(patrimoineListFileName);
        Files.write(patrimoineListFile.toPath(), "existing content;".getBytes());

        File patrimoineFile = new File(patrimoine.nom() + extensionFile);
        when(functions.serialize(patrimoine)).thenReturn(patrimoineFile);

        when(bucketComponent.download(patrimoineListFileName)).thenReturn(patrimoineListFile);

        when(functions.writeToTxt(anyString(), eq(patrimoineListFileName))).thenAnswer(invocation -> {
            String content = invocation.getArgument(0);
            Files.write(patrimoineListFile.toPath(), content.getBytes());
            return patrimoineListFile;
        });

        Patrimoine result = services.create(patrimoine);

        verify(bucketComponent).upload(patrimoineFile, patrimoine.nom() + extensionFile);

        String expectedList = "existing content;testNom;";
        verify(bucketComponent).upload(any(File.class), eq(patrimoineListFileName));
        String updatedListContent = new String(Files.readAllBytes(patrimoineListFile.toPath()));
        assertEquals(expectedList, updatedListContent);

        assertEquals(patrimoine, result);

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

        when(bucketComponent.download(eq("patrimoine_list.txt"))).thenReturn(tempFile);
        when(functions.decodeFile(tempFile)).thenReturn(patrimoine);

        assertEquals(1, services.getAllPatrimoine().size());
    }
    @Test
    public void testDeletePossession() throws IOException {
        String patrimoineName = "testPatrimoine";
        String possessionName = "testPossession";

        Personne possesseur = new Personne("nomTest");
        Possession possessionToDelete = new Materiel("testPossession", LocalDate.now(), 1000, LocalDate.now(), 10.0);
        Set<Possession> possessions = new HashSet<>();
        possessions.add(possessionToDelete);

        Patrimoine patrimoine = new Patrimoine(patrimoineName, possesseur, LocalDate.now(), possessions);

        when(bucketComponent.download(anyString())).thenReturn(patrimoineFile);
        when(functions.decodeFile(patrimoineFile)).thenReturn(patrimoine);
        when(functions.serialize(any(Patrimoine.class))).thenReturn(patrimoineFile);

        String result = services.deletePossession(patrimoineName, possessionName);

        verify(bucketComponent).upload(patrimoineFile, patrimoineName + extensionFile);
        assertEquals("la possession est supprimé avec succes", result);
    }

    @Test
    public void testFindPatrimoineByName() throws IOException {
        String patrimoineName = "testPatrimoine";
        Personne possesseur = new Personne("nomTest");
        Set<Possession> possessions = new HashSet<>();
        Patrimoine expectedPatrimoine = new Patrimoine(patrimoineName, possesseur, LocalDate.now(), possessions);

        when(bucketComponent.download(anyString())).thenReturn(patrimoineFile);
        when(functions.decodeFile(patrimoineFile)).thenReturn(expectedPatrimoine);

        Patrimoine result = services.findPatrimoineByName(patrimoineName);

        assertEquals(expectedPatrimoine, result);
    }
}
