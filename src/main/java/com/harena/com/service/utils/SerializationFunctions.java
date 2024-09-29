package com.harena.com.service.utils;

import org.springframework.stereotype.Service;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.serialisation.Serialiseur;

import java.io.*;
import java.nio.file.Files;

import java.util.Base64;

@Service
public class SerializationFunctions<T> {
    private final Serialiseur<T> serialiseur = new Serialiseur<>();

    public File serialize(T objectToSerialize,String fileName) throws IOException {
        String base64String = serialiseur.serialise(objectToSerialize);
        return writeToTxt(base64String , fileName+".txt");
    }

    public File writeToTxt(String base64String, String fileName) {
        File file = new File("/tmp/" + fileName);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(base64String);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    public T decodeFile (File file) throws IOException {
        String encodedTxt = new String(Files.readAllBytes(file.toPath()));
        return serialiseur.deserialise(encodedTxt);
    }
}
