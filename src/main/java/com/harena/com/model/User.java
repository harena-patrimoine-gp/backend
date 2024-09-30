package com.harena.com.model;

import lombok.Setter;
import school.hei.patrimoine.modele.Patrimoine;

import java.io.Serializable;

public  record User(String name, String email, String password) implements Serializable {
    public User setPassword(String newPassword) {
        return new User(this.name, this.email, newPassword);
    }
}
