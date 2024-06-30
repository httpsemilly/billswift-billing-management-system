/* package com.uj.billswift.clients;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientsRepository extends JpaRepository<Clients, Long> {
} */

package com.uj.billswift.clients;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.*;

public interface ClientsRepository extends JpaRepository<Clients, Long> {
    Optional<List<Clients>> findByCompanyId(String companyId);
    Optional<Clients> findByIdAndCompanyId(Long id, String companyId);
}
