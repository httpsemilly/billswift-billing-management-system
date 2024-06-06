package com.uj.billswift.infra.dto;

public record RegisterRequestDTO(String name, String email, String cnpj, String password, String stateRegistration) {}
