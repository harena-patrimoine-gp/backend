package com.harena.com.services;

import com.harena.com.file.BucketComponent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.serialisation.Serialiseur;

import java.io.*;
import java.nio.file.Files;
import java.util.Base64;
@Service
public class SerializationFunctions {
    Serialiseur<Patrimoine> serialiseur = new Serialiseur<>();

    public File serialize(Patrimoine patrimoine) throws IOException {
        String base64String = serialiseur.serialise(patrimoine);
        return writeBase64ToTxt(base64String , patrimoine.nom()+".txt");
    }

    private File writeBase64ToTxt(String base64String, String filePath) {
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
