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
import school.hei.patrimoine.modele.possession.Possession;

import java.io.File;
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
    public void testGetAllPatrimoineWithMultipleEntries() throws Exception {
        Personne possesseur1 = new Personne("nomTest1");
        Set<Possession> possessions1 = new HashSet<>();
        Patrimoine patrimoine1 = new Patrimoine("testNom1", possesseur1, LocalDate.now(), possessions1);

        Personne possesseur2 = new Personne("nomTest2");
        Set<Possession> possessions2 = new HashSet<>();
        Patrimoine patrimoine2 = new Patrimoine("testNom2", possesseur2, LocalDate.now(), possessions2);

        File tempFile = Files.createTempFile("test", ".txt").toFile();
        Files.write(tempFile.toPath(), "testNom1;testNom2;".getBytes());

        when(bucketComponent.download(eq("patrimoine_list.txt"))).thenReturn(tempFile);
        when(functions.decodeFile(any(File.class))).thenAnswer(invocation -> {
            File file = invocation.getArgument(0);
            String content = new String(Files.readAllBytes(file.toPath()));
            if (content.contains("testNom1")) {
                return patrimoine1;
            } else if (content.contains("testNom2")) {
                return patrimoine2;
            }
            return null;
        });

        assertEquals(2, services.getAllPatrimoine().size());
    }

}
