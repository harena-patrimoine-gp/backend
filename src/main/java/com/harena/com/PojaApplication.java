package com.harena.com;

import com.harena.com.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
@PojaGenerated
public class PojaApplication {
  public static void main(String[] args) {
    SpringApplication.run(PojaApplication.class, args);
  }
}
