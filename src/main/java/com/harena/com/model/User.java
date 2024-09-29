package com.harena.com.model;

import school.hei.patrimoine.modele.Patrimoine;

public  record User(String name, String email, String password, Patrimoine patrimoine){
}
