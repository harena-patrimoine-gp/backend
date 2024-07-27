package com.harena.com.jaksonConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.harena.com.jaksonConfig.mixinSerializer.ArgentMixin;
import com.harena.com.jaksonConfig.mixinSerializer.FluxArgentMixin;
import com.harena.com.jaksonConfig.mixinSerializer.MaterielMixin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import school.hei.patrimoine.modele.possession.Argent;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.Materiel;
@Configuration
public class JacksonConfig {

        @Bean
        public MappingJackson2HttpMessageConverter jsonHttpMessageConverter() {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.addMixIn(Materiel.class, MaterielMixin.class);
            objectMapper.addMixIn(FluxArgent.class , FluxArgentMixin.class);
            objectMapper.addMixIn(Argent.class , ArgentMixin.class);
            return new MappingJackson2HttpMessageConverter(objectMapper);
        }

    }

