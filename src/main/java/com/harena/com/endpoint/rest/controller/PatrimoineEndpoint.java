package com.harena.com.endpoint.rest.controller;

import com.harena.com.file.BucketComponent;
import com.harena.com.services.SerializationFunctions;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import school.hei.patrimoine.modele.Patrimoine;

import java.io.File;
import java.io.IOException;

@RestController
@AllArgsConstructor
public class PatrimoineEndpoint {
    private final SerializationFunctions serializationFunctions;
    private final BucketComponent bucketComponent;

    @GetMapping("/patrimoines/{nom}")
    public Patrimoine getPatrimoineByName(@PathVariable String nom) throws IOException {
        File file = bucketComponent.download(nom+".txt");
        return serializationFunctions.decodeFile(file);
    }
}
