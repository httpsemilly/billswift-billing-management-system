package com.uj.billswift.infra.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uj.billswift.company.Company;
import com.uj.billswift.infra.security.SecurityFilter;
import com.uj.billswift.infra.security.TokenService;

import jakarta.servlet.http.HttpServletRequest;

import com.uj.billswift.company.CompanyRepository;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    TokenService tokenService;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    SecurityFilter securityFilter;
    
    @GetMapping
    public ResponseEntity<Company> getCompany(HttpServletRequest request) {
        var token = securityFilter.recoverToken(request);
        var login = tokenService.validateToken(token);

        Company company = companyRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("Company not found"));

        return ResponseEntity.ok(company);
    }
}
