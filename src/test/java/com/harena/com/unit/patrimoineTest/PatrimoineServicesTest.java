package com.harena.com.unit.patrimoineTest;

import com.harena.com.service.PatrimoineServices;
import com.harena.com.file.BucketComponent;
import com.harena.com.service.utils.SerializationFunctions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.Materiel;
import school.hei.patrimoine.modele.possession.Possession;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PatrimoineServicesTest {
    @Mock
    private BucketComponent bucketComponent;
    @Mock
    private SerializationFunctions functions;
    @InjectMocks
    private PatrimoineServices services;
    private final String patrimoineListFileName = "patrimoine_list.txt";
    private final String extensionFile = ".txt";

    @BeforeEach
    public void setup() {
        bucketComponent = Mockito.mock(BucketComponent.class);
        functions = Mockito.mock(SerializationFunctions.class);
        services = new PatrimoineServices(bucketComponent, functions);

    }


    @Test
    public void testCreatePatrimoineWithNull() {
        assertThrows(NullPointerException.class, () -> {
            services.create(null);
        });
    }


}
