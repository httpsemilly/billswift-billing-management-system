package com.uj.billswift.company;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "companies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String email;
    private String cnpj;
    private String password;
}
