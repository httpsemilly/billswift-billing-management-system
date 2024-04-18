package com.uj.billswift.company;

import jakarta.persistence.*;

@Entity
// @Table(name = "companies")

public class Company {
    // definir informações sobre a empresa, como nome, cnpj, email, senha etc
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // definir getters e setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
