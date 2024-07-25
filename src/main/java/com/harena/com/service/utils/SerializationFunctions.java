package com.harena.com.service.utils;

import org.springframework.stereotype.Service;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.serialisation.Serialiseur;

import java.io.*;
import java.nio.file.Files;

import java.util.Base64;

@Service
public class SerializationFunctions {
    private Serialiseur<Patrimoine> serialiseur = new Serialiseur<>();

    public File serialize(Patrimoine patrimoine) throws IOException {
        String base64String = serialiseur.serialise(patrimoine);
        return writeToTxt(base64String , patrimoine.nom()+".txt");
    }

    private File writeToTxt(String base64String, String filePath) {
        File file = new File(filePath);
        try (FileWriter writer = new FileWriter(filePath)) {
             writer.write(base64String);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public Patrimoine decodeFile (File file) throws IOException {
        String encodedTxt = new String(Files.readAllBytes(file.toPath()));
        return serialiseur.deserialise(encodedTxt);
    }
}
