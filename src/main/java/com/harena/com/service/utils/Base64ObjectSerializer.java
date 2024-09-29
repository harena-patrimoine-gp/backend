package com.harena.com.service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harena.com.model.Patrimoine;
import com.harena.com.model.Person;
import com.harena.com.model.User;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64ObjectSerializer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Méthode pour encoder un objet en Base64 et l'écrire dans un fichier texte
    public static File encodeObjectToFile(Object object, String fileName) throws IOException {
        // Sérialiser l'objet en JSON
        String jsonString = objectMapper.writeValueAsString(object);

        // Encoder le JSON en Base64
        String base64String = Base64.getEncoder().encodeToString(jsonString.getBytes(StandardCharsets.UTF_8));

        // Écrire la chaîne encodée en Base64 dans un fichier texte
        File file = new File("/tmp/" + fileName);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(base64String);
        }

        return file;
    }

    // Méthode pour lire un fichier texte et décoder un objet de Base64
    public static <T> T decodeObjectFromFile(File file, Class<T> clazz) throws IOException {
        // Lire le contenu du fichier
        String base64String = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

        // Décoder la chaîne Base64 pour obtenir le JSON
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        String jsonString = new String(decodedBytes, StandardCharsets.UTF_8);

        // Désérialiser le JSON pour retrouver l'objet d'origine
        return objectMapper.readValue(jsonString, clazz);
    }

    public static void main(String[] args) {
        try {
            // Exemple d'objet à sérialiser (Person ici)
            Person person = new Person("Alice");

            // Encoder l'objet et écrire dans un fichier temporaire
            File encodedFile = encodeObjectToFile(person, "person_data.txt");
            System.out.println("Fichier encodé créé à : " + encodedFile.getAbsolutePath());

            // Lire et décoder l'objet à partir du fichier
            Person decodedPerson = decodeObjectFromFile(encodedFile, Person.class);
            System.out.println("Objet décodé : " + decodedPerson);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Exemple de classe Person à sérialiser

}
