package com.harena.com.PatrimooineEndpoint;

import com.harena.com.endpoint.rest.controller.PatrimoineEndpoint;
import com.harena.com.file.BucketComponent;
import com.harena.com.service.PatrimoineServices;
import com.harena.com.service.utils.SerializationFunctions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.Possession;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatrimoineEndpoint.class)
public class PatrimoineEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SerializationFunctions serializationFunctions;

    @MockBean
    private BucketComponent bucketComponent;

    @MockBean
    private PatrimoineServices services;

    private Patrimoine patrimoine;
    private File tempFile;

    @BeforeEach
    public void setup() throws IOException {
        Personne possesseur = new Personne("nomTest");

        Set<Possession> possessions = new HashSet<>();

        patrimoine = new Patrimoine("testNom", possesseur, LocalDate.now(), possessions);

        tempFile = Files.createTempFile("test", ".txt").toFile();
        Files.write(tempFile.toPath(), "test content".getBytes());

        Mockito.when(bucketComponent.download(eq("testNom.txt"))).thenReturn(tempFile);
        Mockito.when(bucketComponent.download("randaom.txt")).thenReturn(null);
        Mockito.when(serializationFunctions.decodeFile(any(File.class))).thenReturn(patrimoine);
        Mockito.when(bucketComponent.download(eq("patrimoine_list.txt"))).thenReturn(tempFile);
        Mockito.when(services.create(any(Patrimoine.class))).thenReturn(patrimoine);
        Mockito.when(services.getAllPatrimoine()).thenReturn(Collections.singletonList(patrimoine));
    }


    @Test
    public void testGetPatrimoineByName() throws Exception {
        mockMvc.perform(get("/patrimoines/testNom"))
                .andExpect(status().isOk());

    }


    @Test
    public void testCreateUpdate() throws Exception {
        String patrimoineJson = "{\"nom\": \"testNom\", \"possesseur\": {}, \"t\": \"2024-07-26\", \"possessions\": []}";

        mockMvc.perform(put("/patrimoines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patrimoineJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom", is("testNom")));
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/patrimoines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom", is("testNom")));
    }

}



