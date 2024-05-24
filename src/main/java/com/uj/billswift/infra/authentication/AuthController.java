package com.uj.billswift.infra.authentication;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uj.billswift.company.Company;
import com.uj.billswift.company.CompanyRepository;
import com.uj.billswift.infra.dto.LoginRequestDTO;
import com.uj.billswift.infra.dto.RegisterRequestDTO;
import com.uj.billswift.infra.dto.ResponseDTO;
import com.uj.billswift.infra.security.TokenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/billswift")
@RequiredArgsConstructor
public class AuthController {
    private final CompanyRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login") 
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        Optional<Company> companyLogin = this.repository.findByEmail(body.email());

        if(companyLogin.isEmpty()) {
            return ResponseEntity.status(404).body("Company not found");
        }

        Company company = companyLogin.get();

        if(passwordEncoder.matches(body.password(), company.getPassword())) {
            String token = this.tokenService.generateToken(company);
            return ResponseEntity.ok(new ResponseDTO(company.getName(), token));
        }

        return ResponseEntity.status(401).body("Invalid password");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body) {
        Optional<Company> companyCheckEmail = this.repository.findByEmail(body.email());

        if(companyCheckEmail.isPresent()) {
            return ResponseEntity.status(400).body("Email already registered");
        }

            Company newCompany = new Company();
            newCompany.setPassword(passwordEncoder.encode(body.password()));
            newCompany.setEmail(body.email());
            newCompany.setName(body.name());
            newCompany.setCnpj(body.cnpj());
            this.repository.save(newCompany);

            String token = this.tokenService.generateToken(newCompany);
            return ResponseEntity.ok(new ResponseDTO(newCompany.getName(), token));
    }
}
