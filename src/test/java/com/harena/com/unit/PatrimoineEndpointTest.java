package com.harena.com.unit;

import com.harena.com.endpoint.rest.controller.PatrimoineEndpoint;
import com.harena.com.file.BucketComponent;
import com.harena.com.service.utils.SerializationFunctions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import school.hei.patrimoine.modele.Patrimoine;

import java.io.File;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatrimoineEndpointTest {

    @Mock
    private SerializationFunctions serializationFunctions;

    @Mock
    private BucketComponent bucketComponent;

    @InjectMocks
    private PatrimoineEndpoint patrimoineEndpoint;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patrimoineEndpoint).build();
    }

   /* @Test
    public void getPatrimoineByName_shouldReturn200() throws Exception {
        File file = new File("test.txt");
        Patrimoine patrimoine = new Patrimoine();
        when(bucketComponent.download(anyString())).thenReturn(file);
        when(serializationFunctions.decodeFile(file)).thenReturn(patrimoine);

        mockMvc.perform(get("/patrimoines/{nom}", "TestPatrimoine")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }*/

    @Test
    public void getPatrimoineByName_shouldReturn404WhenFileNotFound() throws Exception {
        when(bucketComponent.download(anyString())).thenThrow(new IOException("File not found"));

        mockMvc.perform(get("/patrimoines/{nom}", "NonExistentPatrimoine")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getPatrimoineByName_shouldReturn500WhenDecodingFails() throws Exception {
        File file = new File("test.txt");
        when(bucketComponent.download(anyString())).thenReturn(file);
        when(serializationFunctions.decodeFile(file)).thenThrow(new IOException("Decoding failed"));

        mockMvc.perform(get("/patrimoines/{nom}", "TestPatrimoine")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
