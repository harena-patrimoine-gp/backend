package com.harena.com.endpoint.rest.controller;

import com.harena.com.file.BucketComponent;
import com.harena.com.service.PatrimoineServices;
import com.harena.com.service.utils.SerializationFunctions;
import lombok.AllArgsConstructor;
import org.joda.time.Duration;
import org.springframework.web.bind.annotation.*;
import school.hei.patrimoine.modele.Patrimoine;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
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
        return services.create(patrimoine);
    }

    @GetMapping("/patrimoines/all")
    public List<Patrimoine> getAll() throws IOException {
        return services.getAllPatrimoine();
    }

    @GetMapping("/file")
    public String url() throws IOException {
        return new String(Files.readAllBytes(bucketComponent.download("patrimoine_list.txt" ).toPath()));
    }
}
