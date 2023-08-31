package next.school.cesar.desafiospring.controllers;

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

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import next.school.cesar.desafiospring.dto.ClientDTO;
import next.school.cesar.desafiospring.entities.Client;
import next.school.cesar.desafiospring.services.ClientService;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClientController {
    
    private final ClientService clientService;
    
    @GetMapping
    public ResponseEntity<List<Client>> listAll() {
        List<Client> listClients = this.clientService.listAll();
        return new ResponseEntity<>(listClients, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Client> create(@RequestBody @Valid ClientDTO clientDTO) {
        Client client = clientService.create(clientDTO);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable long id) {
        Client client = this.clientService.getById(id);
        if (client != null){
            return new ResponseEntity<>(client, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable long id, @RequestBody @Valid ClientDTO clientDTO) {
        Client client = clientService.update(id, clientDTO);
        if (client != null){
            return new ResponseEntity<>(client, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        if (clientService.delete(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
