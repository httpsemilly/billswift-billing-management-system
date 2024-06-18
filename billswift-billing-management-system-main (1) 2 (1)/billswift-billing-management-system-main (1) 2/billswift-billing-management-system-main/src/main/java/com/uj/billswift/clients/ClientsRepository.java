package com.uj.billswift.clients;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientsRepository extends JpaRepository<Clients, Long> {
    Clients findByEmail(String email); // Método para buscar cliente pelo email
}