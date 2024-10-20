package com.harena.com.service.utils;

import school.hei.patrimoine.modele.Devise;

public class StringToDeviseMapper {
    public static Devise stringToDevise(String devise){
        return  switch (devise) {
            case "MGA" -> Devise.MGA;
            case "EUR" -> Devise.EUR;
            case "CAD" -> Devise.CAD;
            default -> throw new IllegalArgumentException("Devise non prise en charge: " + devise);
        };

    }
}
