package ma.enset.subscription_mangement_system.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
@Builder @ToString
public class Abonnement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateAb;
    @Enumerated(EnumType.STRING)
    private TYPE typeAb;

    private Float solde;

    private Float montant;
    @ManyToOne
    private Client client;

}
