package com.harena.com.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class Person implements Serializable {
    private String name;
    public Person(String name){
        this
                .name=name;
    }

}
