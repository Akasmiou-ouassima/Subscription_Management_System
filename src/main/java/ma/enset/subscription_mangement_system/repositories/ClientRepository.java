package ma.enset.subscription_mangement_system.repositories;

import ma.enset.subscription_mangement_system.entities.Abonnement;
import ma.enset.subscription_mangement_system.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    Page<Client> findByNomContains(String kw, Pageable pageable);
    @Query("Select c from Client c where c.nom like :x ")
    Client chercherClient(@Param("x") String nom);

}
