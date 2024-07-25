package com.harena.com.jakson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import school.hei.patrimoine.modele.possession.Argent;
import school.hei.patrimoine.modele.possession.Possession;

import java.io.IOException;

public class CustomDeserialiseur extends JsonDeserializer<Possession> {
    @Override
    public Possession deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        JsonNode node = jp.getCodec().readTree(jp);
        String type = node.get("type").asText();

        switch (type) {
            case "Argent":
                return new ObjectMapper().treeToValue(node, Argent.class);
            default:
                throw new IOException("Type inconnu: " + type);
        }
    }
}
