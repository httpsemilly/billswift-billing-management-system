package com.uj.billswift.company;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {
    Optional<Company> findByEmail(String email);
    Optional<Company> findByCnpj(String cnpj);
    Optional<Company> findByStateRegistration(String stateRegistration);
}
