package com.harena.com;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PatrimoineController {

    @GetMapping("/patrimoines")
    public ResponseEntity<List<Patrimoine>> getPatrimoines(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "page_size", required = false, defaultValue = "10") int pageSize) {

        return ResponseEntity.ok(List.of(new Patrimoine("Patrimoine1"), new Patrimoine("Patrimoine2")));
    }
}

class Patrimoine {
    private String nom;

    public Patrimoine(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
