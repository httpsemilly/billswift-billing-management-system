package com.uj.billswift.infra.authentication;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AuthController {
    private final CompanyRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    // Função auxiliar para criar o corpo da resposta JSON
    private ResponseEntity<Map<String, String>> createErrorResponse(String message, Integer status) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", message);
        return ResponseEntity.status(status).body(responseBody);
    }

    @PostMapping("/login") 
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        Optional<Company> companyLogin = this.repository.findByEmail(body.email());

        if(companyLogin.isEmpty()) {
            return createErrorResponse("Company not found.", 404);
            //return ResponseEntity.status(404).body("Company not found");
        }

        Company company = companyLogin.get();

        if(passwordEncoder.matches(body.password(), company.getPassword())) {
            String token = this.tokenService.generateToken(company);
            return ResponseEntity.ok(new ResponseDTO(company.getName(), token));
        }

        return createErrorResponse("Invalid password.", 401);
        //return ResponseEntity.status(401).body("Invalid password");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body) {
        Optional<Company> companyCheckEmail = this.repository.findByEmail(body.email());
        if(companyCheckEmail.isPresent()) {
            return createErrorResponse("Email já registrado.", 400);
        }
        
        Optional<Company> companyCheckCnpj = this.repository.findByCnpj(body.cnpj());
        if(companyCheckCnpj.isPresent()) {
            return createErrorResponse("Um usuário com o CNPJ já está registrado.", 400);
        }

        Optional<Company> companyCheckStateRegistration = this.repository.findByStateRegistration(body.stateRegistration());
        if(companyCheckStateRegistration.isPresent()) {
            return createErrorResponse("Um usuário com a inscrição estadual já está registrado.", 400);
        }

            Company newCompany = new Company();
            newCompany.setPassword(passwordEncoder.encode(body.password()));
            newCompany.setEmail(body.email());
            newCompany.setName(body.name());
            newCompany.setCnpj(body.cnpj());
            newCompany.setStateRegistration(body.stateRegistration());
            
            this.repository.save(newCompany);

            String token = this.tokenService.generateToken(newCompany);
            return ResponseEntity.ok(new ResponseDTO(newCompany.getName(), token));
    }
}
