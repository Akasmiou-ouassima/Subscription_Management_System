package ma.enset.subscription_mangement_system.service;

import ma.enset.subscription_mangement_system.entities.Abonnement;
import ma.enset.subscription_mangement_system.entities.Client;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAbonnementService {
    Client saveClient(Client client);
    Abonnement saveAbonnement(Abonnement abonnement);

    List<Client> getAllClients();
    List<Abonnement> getAllAbonnements();

    Client getClientById(Long id);
    Abonnement getAbonnementById(Long id);

    Client updateClient(Client client);
   Abonnement updateAbonnement(Abonnement abonnement);

    void deleteClientById(Long id);
    void deleteAbonnementById(Long id);
     List<Abonnement> getByClient_Id(Long clientId);

}
