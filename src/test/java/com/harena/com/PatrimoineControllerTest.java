package com.harena.com;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class PatrimoineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getPatrimoines_shouldReturn200() throws Exception {
        mockMvc.perform(get("/patrimoines")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void crupdatePatrimoines_shouldReturn200() throws Exception {
        String requestBody = "{\"data\": [{\"nom\": \"Patrimoine1\", \"valeur_comptable\": 1000}]}";
        mockMvc.perform(put("/patrimoines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    public void getPatrimoineByNom_shouldReturn200() throws Exception {
        mockMvc.perform(get("/patrimoines/Patrimoine1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void crupdatePatrimoinePossessions_shouldReturn200() throws Exception {
        String requestBody = "{\"data\": [{\"type\": \"MATERIEL\", \"materiel\": {\"nom\": \"Materiel1\", \"valeur_comptable\": 500}}]}";
        mockMvc.perform(put("/patrimoines/Patrimoine1/possessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    public void getPatrimoinePossessions_shouldReturn200() throws Exception {
        mockMvc.perform(get("/patrimoines/Patrimoine1/possessions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getPatrimoinePossessionByNom_shouldReturn200() throws Exception {
        mockMvc.perform(get("/patrimoines/Patrimoine1/possessions/Possession1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePatrimoinePossessionByNom_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/patrimoines/Patrimoine1/possessions/Possession1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getPatrimoineFluxImpossibles_shouldReturn200() throws Exception {
        mockMvc.perform(get("/patrimoines/Patrimoine1/flux-impossibles")
                        .param("debut", "2024-01-01")
                        .param("fin", "2024-12-31")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getPatrimoineGraph_shouldReturn200() throws Exception {
        mockMvc.perform(get("/patrimoines/Patrimoine1/graphe")
                        .param("debut", "2024-01-01")
                        .param("fin", "2024-12-31")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
