package com.uj.billswift.clients;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Anotação @RestController indica que esta classe é um controlador REST
@RestController
@RequestMapping("/api/clients") // Mapeia todas as requisições para /api/clients
public class ClientsController {

    // Injeta a dependência do serviço de clientes
    @Autowired
    private ClientsService clientsService;

    // Endpoint para obter todos os clientes
    @GetMapping
    public ResponseEntity<List<Clients>> getAllClients() {
        List<Clients> clients = clientsService.getAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK); // Retorna a lista de clientes com status HTTP 200
    }

    // Endpoint para obter um cliente pelo seu ID
    @GetMapping("/{id}")
    public ResponseEntity<Clients> getClientsById(@PathVariable Long id) {
        Clients clients = clientsService.getClientsById(id);
        return new ResponseEntity<>(clients, HttpStatus.OK); // Retorna o cliente com status HTTP 200
    }

    // Endpoint para obter um cliente pelo seu email
    @GetMapping("/email/{email}")
    public ResponseEntity<Clients> getClientByEmail(@PathVariable String email) {
        Clients clients = clientsService.getClientByEmail(email);
        return new ResponseEntity<>(clients, HttpStatus.OK); // Retorna o cliente com status HTTP 200
    }

    // Endpoint para criar um novo cliente
    @PostMapping
    public ResponseEntity<Clients> createClient(@RequestBody Clients clients) {
        Clients createdClient = clientsService.createClients(clients);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED); // Retorna o cliente criado com status HTTP 201
    }

    // Endpoint para atualizar um cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<Clients> updateClient(@PathVariable Long id, @RequestBody Clients clientDetails) {
        try {
            Clients updatedClient = clientsService.updateClient(id, clientDetails);
            return ResponseEntity.ok(updatedClient); // Retorna o cliente atualizado com status HTTP 200
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Retorna status HTTP 404 se o cliente não for encontrado
        }
    }

    // Endpoint para deletar um cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientsService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna status HTTP 204 (sem conteúdo) após a exclusão
    }
}
