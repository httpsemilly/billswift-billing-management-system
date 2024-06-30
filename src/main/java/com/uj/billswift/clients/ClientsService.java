package com.uj.billswift.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uj.billswift.company.Company;
import com.uj.billswift.company.CompanyService;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ClientsService {

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private CompanyService companyService;

    public List<Clients> getAllClients(HttpServletRequest request) {
        Company company = companyService.getCompany(request);
        String companyId = company.getId();
        
        return clientsRepository.findByCompanyId(companyId)
               .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
    }

    public Clients getClientsById(Long id, HttpServletRequest request) {
        Company company = companyService.getCompany(request);
        String companyId = company.getId();
        
        return clientsRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado para esta empresa."));
    }

    public Clients createClients(Clients clients, HttpServletRequest request) {
        Company company = companyService.getCompany(request);

        String companyId = company.getId();
        clients.setCompanyId(companyId);
        
        return clientsRepository.save(clients);
    }

    public Clients updateClient(Long id, Clients clientDetails, HttpServletRequest request) {
        Company company = companyService.getCompany(request);
        String companyId = company.getId();

        Clients client = clientsRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado para esta empresa."));

/*         Clients client = clientsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado.")); */
        client.setName(clientDetails.getName());
        client.setPhone(clientDetails.getPhone());
        client.setState(clientDetails.getState());
        client.setNeighborhood(clientDetails.getNeighborhood());
        client.setCpf(clientDetails.getCpf());
        client.setZipCode(clientDetails.getZipCode());
        client.setCity(clientDetails.getCity());
        client.setAddress(clientDetails.getAddress());
        return clientsRepository.save(client);
    }

    public void deleteClient(Long id, HttpServletRequest request) {
        Company company = companyService.getCompany(request);
        String companyId = company.getId();

        Clients client = clientsRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado para esta empresa."));
        
        clientsRepository.delete(client);
/*         Clients client = clientsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        clientsRepository.delete(client); */
    }
}