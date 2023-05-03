package ma.enset.subscription_mangement_system.web;

import ma.enset.subscription_mangement_system.entities.Abonnement;
import ma.enset.subscription_mangement_system.entities.Client;
import ma.enset.subscription_mangement_system.repositories.AbonnementRepository;
import ma.enset.subscription_mangement_system.repositories.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class ClientRestController {
    private ClientRepository clientRepository;


    public ClientRestController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/clients")
    public List<Client> getClients() {
        List<Client> clients = clientRepository.findAll();
        for (Client client : clients) {
            client.getAbonnements().size(); // chargement paresseux des abonnements pour éviter la boucle infinie
        }
        return clients;
    }

    @GetMapping("/clients/{id}")
    public Client getClientById(@PathVariable Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Client %d not found", id)));
    }

    @PostMapping("/clients")
    public Client createClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    @PutMapping("/clients/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client client) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Client %d not found", id)));
        existingClient.setNom(client.getNom());
        existingClient.setEmail(client.getEmail());
        existingClient.setUsername(client.getUsername());
        existingClient.setAbonnements(client.getAbonnements());
        return clientRepository.save(existingClient);
    }

    @DeleteMapping("/clients/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientRepository.deleteById(id);
    }
}

