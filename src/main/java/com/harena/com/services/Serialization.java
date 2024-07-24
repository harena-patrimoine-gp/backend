package com.harena.com.services;

import school.hei.patrimoine.modele.Patrimoine;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.io.*;
import java.util.Base64;

public class Serialization {
    public static File serialize(Patrimoine patrimoine) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(patrimoine);
        oos.flush();
        String base64String = Base64.getEncoder().encodeToString(baos.toByteArray());
        return writeBase64ToTxt(base64String , patrimoine.nom()+".txt");
    }

    private static File writeBase64ToTxt(String base64String, String filePath) {
        File file = new File(filePath);
        try (FileWriter writer = new FileWriter(filePath)) {
             writer.write(base64String);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
