package com.uj.billswift.clients;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// A anotação @Configuration indica que esta classe é uma configuração do Spring
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Override do método addCorsMappings para configurar o CORS (Cross-Origin Resource Sharing)
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configuração para permitir requisições de outras origens
        registry.addMapping("/**") // Permite CORS para todos os endpoints da aplicação
                .allowedOrigins("http://localhost:8084") // Permite requisições apenas desta origem específica
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS"); // Permite apenas estes métodos HTTP
    }
}