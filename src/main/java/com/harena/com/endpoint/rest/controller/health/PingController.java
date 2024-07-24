package com.harena.com.endpoint.rest.controller.health;

import com.harena.com.PojaGenerated;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import school.hei.patrimoine.modele.Patrimoine;

@PojaGenerated
@RestController
@AllArgsConstructor
public class PingController {

  public static final ResponseEntity<String> OK = new ResponseEntity<>("OK", HttpStatus.OK);
  public static final ResponseEntity<String> KO =
      new ResponseEntity<>("KO", HttpStatus.INTERNAL_SERVER_ERROR);



  @GetMapping("/ping")
  public String ping() {
    return "pong";
  }


}
