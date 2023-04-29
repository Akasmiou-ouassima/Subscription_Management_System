package ma.enset.subscription_mangement_system.repositories;

import ma.enset.subscription_mangement_system.entities.Abonnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface AbonnementRepository extends JpaRepository<Abonnement,Long> {
    List<Abonnement> findByClient_Id(Long clientId);

}
