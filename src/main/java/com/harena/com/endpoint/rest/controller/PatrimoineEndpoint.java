package com.harena.com.endpoint.rest.controller;

import com.harena.com.file.BucketComponent;
import com.harena.com.services.PatrimoineServices;
import com.harena.com.services.SerializationFunctions;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import school.hei.patrimoine.modele.Patrimoine;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class PatrimoineEndpoint {
    private final SerializationFunctions serializationFunctions;
    private final BucketComponent bucketComponent;
    private final PatrimoineServices services;

    @GetMapping("/patrimoines/{nom}")
    public Patrimoine getPatrimoineByName(@PathVariable String nom) throws IOException {
        File file = bucketComponent.download(nom+".txt");
        return serializationFunctions.decodeFile(file);
    }

    @PutMapping("/patrimoines")
    public Patrimoine createUpdate(@RequestBody Patrimoine patrimoine) throws IOException {
        return services.createUpdate(patrimoine);
    }

    @GetMapping("/patrimoines")
    public List<Patrimoine> getAll() throws IOException {
        return services.getAllPatrimoine();
    }
}
