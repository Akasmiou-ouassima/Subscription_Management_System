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
public class AbonnementRestController {
    private AbonnementRepository abonnementRepository;
    private ClientRepository clientRepository;
    public AbonnementRestController(AbonnementRepository abonnementRepository, ClientRepository clientRepository) {
        this.abonnementRepository = abonnementRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/abonnements")
    public List<Abonnement> getAbonnements() {
        List<Abonnement> abonnements = abonnementRepository.findAll();
        for (Abonnement abonnement : abonnements) {
            abonnement.getClient().getId(); // chargement paresseux du client pour éviter la boucle infinie
        }
        return abonnements;
    }

    @GetMapping("/abonnements/{id}")
    public Abonnement getAbonnementById(@PathVariable Long id) {
        return abonnementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Subscription %d not found", id)));
    }

    @PostMapping("/abonnements")
    public Abonnement createAbonnement(@RequestBody Abonnement abonnement) {
        // Vérifier si le client existe
        Client client = clientRepository.findById(abonnement.getClient().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Client %d not found", abonnement.getClient().getId())));

        // Associer le client à l'abonnement
        abonnement.setClient(client);

        return abonnementRepository.save(abonnement);
    }


    @PutMapping("/abonnements/{id}")
    public Abonnement updateAbonnement(@PathVariable Long id, @RequestBody Abonnement abonnement) {
        // Vérifier si l'abonnement existe
        Abonnement existingAbonnement = abonnementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Subscription %d not found", id)));

        // Vérifier si le client existe
        Client client = clientRepository.findById(abonnement.getClient().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Client %d not found", abonnement.getClient().getId())));

        // Mettre à jour les informations de l'abonnement
        existingAbonnement.setDateAb(abonnement.getDateAb());
        existingAbonnement.setSolde(abonnement.getSolde());
        existingAbonnement.setMontant(abonnement.getMontant());
        existingAbonnement.setTypeAb(abonnement.getTypeAb());
        existingAbonnement.setClient(client);

        return abonnementRepository.save(existingAbonnement);
    }


    @DeleteMapping("/abonnements/{id}")
    public void deleteAbonnement(@PathVariable Long id) {
        abonnementRepository.deleteById(id);
    }
}
