package com.harena.com.model;

import school.hei.patrimoine.modele.Patrimoine;

import java.io.Serializable;

public  record User(String name, String email, String password) implements Serializable {
}
