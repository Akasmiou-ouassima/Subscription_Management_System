package ma.enset.subscription_mangement_system.service;

import jakarta.transaction.Transactional;
import ma.enset.subscription_mangement_system.entities.Abonnement;
import ma.enset.subscription_mangement_system.entities.Client;
import ma.enset.subscription_mangement_system.repositories.AbonnementRepository;
import ma.enset.subscription_mangement_system.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class AbonnementServiceImpl implements IAbonnementService{
    @Autowired
    private ClientRepository clientRepository;

    public AbonnementServiceImpl(ClientRepository clientRepository, AbonnementRepository abonnementRepository) {
        this.clientRepository = clientRepository;
        this.abonnementRepository = abonnementRepository;
    }

    private AbonnementRepository abonnementRepository;
    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Abonnement saveAbonnement(Abonnement abonnement) {
        return abonnementRepository.save(abonnement);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public List<Abonnement> getAllAbonnements() {
        return abonnementRepository.findAll();
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).get();
    }

    @Override
    public Abonnement getAbonnementById(Long id) {
        return abonnementRepository.findById(id).get();
    }

    @Override
    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Abonnement updateAbonnement(Abonnement abonnement) {
        return abonnementRepository.save(abonnement);
    }

    @Override
    public void deleteClientById(Long id) {
       clientRepository.deleteById(id);
    }

    @Override
    public void deleteAbonnementById(Long id) {
         abonnementRepository.deleteById(id);
    }

    @Override
    public List<Abonnement> getByClient_Id(Long clientId) {
        return abonnementRepository.findByClient_Id(clientId);
    }
}
