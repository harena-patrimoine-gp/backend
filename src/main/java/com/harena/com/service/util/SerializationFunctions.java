package com.harena.com.service.util;

import com.harena.com.file.BucketComponent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.patrimoine.modele.Patrimoine;

import java.io.*;
import java.nio.file.Files;
import java.util.Base64;

@Service
public class SerializationFunctions {
    public File serialize(Patrimoine patrimoine) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(patrimoine);
        oos.flush();
        String base64String = Base64.getEncoder().encodeToString(baos.toByteArray());
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
        byte[] decodedBytes = Base64.getDecoder().decode(encodedTxt);

        try(ByteArrayInputStream bais = new ByteArrayInputStream(decodedBytes);
            ObjectInputStream ois = new ObjectInputStream(bais)){
                return (Patrimoine) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
