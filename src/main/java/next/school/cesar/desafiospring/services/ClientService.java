package next.school.cesar.desafiospring.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import next.school.cesar.desafiospring.dto.ClientDTO;
import next.school.cesar.desafiospring.entities.Client;
import next.school.cesar.desafiospring.repositories.ClientRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClientService {
    
    private final ClientRepository clientRepository;

    public Client create(ClientDTO clientDTO) {
        Client client = clientDTO.toEntity();
        client.setCreatedAt(LocalDate.now());
        client.setUpdatedAt(LocalDate.now());
        return this.clientRepository.save(client);
    }

    public List<Client> listAll() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .collect(Collectors.toList());
    }

    public Client getById(long id) {
        return this.clientRepository.findById(id).orElse(null);
    }

    public Client update(long id, @Valid ClientDTO clientDTO) {
        Client client = this.clientRepository.findById(id).orElse(null);
        if (client != null){
            Client updateClient = clientDTO.toEntityUpdate(client);
            return this.clientRepository.save(updateClient);
        }
        return null;
    }

    public boolean delete(long id) {
        Client client = this.clientRepository.findById(id).orElse(null);
        if (client != null){
            this.clientRepository.delete(client);
            return true;
        }
        return false;
    }

    
}
