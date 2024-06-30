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

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/clients")
public class ClientsController {

    @Autowired
    private ClientsService clientsService;

    @GetMapping
    public ResponseEntity<List<Clients>> getAllClients(HttpServletRequest request) {
        List<Clients> clients = clientsService.getAllClients(request);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clients> getClientsById(@PathVariable Long id, HttpServletRequest request) {
        Clients clients = clientsService.getClientsById(id, request);
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Clients> createClient(@RequestBody Clients clients, HttpServletRequest request) {
        Clients createdClient = clientsService.createClients(clients, request);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clients> updateClient(@PathVariable Long id, @RequestBody Clients clientDetails, HttpServletRequest request) {
        try {
            Clients updatedClient = clientsService.updateClient(id, clientDetails, request);
            return ResponseEntity.ok(updatedClient);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id, HttpServletRequest request) {
        clientsService.deleteClient(id, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}