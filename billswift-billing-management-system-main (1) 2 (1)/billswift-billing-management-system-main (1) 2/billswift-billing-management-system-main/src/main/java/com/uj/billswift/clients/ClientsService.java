package com.uj.billswift.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// Anotação @Service indica que esta classe é um serviço do Spring
@Service
public class ClientsService {

    // Injeta a dependência do repositório de clientes
    @Autowired
    private ClientsRepository clientsRepository;

    // Método para obter todos os clientes
    public List<Clients> getAllClients() {
        return clientsRepository.findAll();
    }

    // Método para obter um cliente pelo seu ID
    public Clients getClientsById(Long id) {
        return clientsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
    }

    // Método para criar um novo cliente
    public Clients createClients(Clients clients) {
        return clientsRepository.save(clients);
    }

    // Método para atualizar um cliente existente
    public Clients updateClient(Long id, Clients clientDetails) {
        // Busca o cliente pelo ID e lança exceção se não for encontrado
        Clients client = clientsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
        
        // Atualiza os detalhes do cliente
        client.setName(clientDetails.getName());
        client.setPhone(clientDetails.getPhone());
        client.setState(clientDetails.getState());
        client.setNeighborhood(clientDetails.getNeighborhood());
        client.setCpf(clientDetails.getCpf());
        client.setZipCode(clientDetails.getZipCode());
        client.setCity(clientDetails.getCity());
        client.setAddress(clientDetails.getAddress());
        
        // Salva as alterações no repositório
        return clientsRepository.save(client);
    }

    // Método para deletar um cliente
    public void deleteClient(Long id) {
        // Busca o cliente pelo ID e lança exceção se não for encontrado
        Clients client = clientsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        
        // Deleta o cliente do repositório
        clientsRepository.delete(client);
    }

    // Método para buscar cliente pelo email
    public Clients getClientByEmail(String email) {
        return clientsRepository.findByEmail(email);
    }
}