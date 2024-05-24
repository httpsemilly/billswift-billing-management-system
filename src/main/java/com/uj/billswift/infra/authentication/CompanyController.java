package com.uj.billswift.infra.authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @GetMapping
    public ResponseEntity<String> getCompany() {
        return ResponseEntity.ok("Sucesso!");
    }
}
