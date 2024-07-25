package com.harena.com.jakson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import school.hei.patrimoine.modele.possession.Possession;

public class RegisterDeserializer {

    public static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addDeserializer(Possession.class, new CustomDeserialiseur());
        objectMapper.registerModule(module);

        return objectMapper;
    }
}
