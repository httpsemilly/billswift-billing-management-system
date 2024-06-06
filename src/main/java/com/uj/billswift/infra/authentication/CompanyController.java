package com.uj.billswift.infra.authentication;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@CrossOrigin(origins = "http://localhost:3000")
public class CompanyController {
    @GetMapping
    public ResponseEntity<Map<String, String>> getCompany() {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Sucesso!");
        return ResponseEntity.ok(responseBody);
    }
}
