package ma.enset.subscription_mangement_system;

import ma.enset.subscription_mangement_system.entities.Abonnement;
import ma.enset.subscription_mangement_system.entities.Client;
import ma.enset.subscription_mangement_system.entities.TYPE;
import ma.enset.subscription_mangement_system.repositories.AbonnementRepository;
import ma.enset.subscription_mangement_system.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class SubscriptionMangementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubscriptionMangementSystemApplication.class, args);
    }
    @Bean
    CommandLineRunner start(ClientRepository clientRepository, AbonnementRepository abonnementRepository){
       return args -> {
            Stream.of("ouassima","Hanane","Mohamed").forEach(name ->{
                Client client=new Client();
                client.setNom(name);
                client.setEmail(name+"@gmail.com");
                client.setUsername(Math.random()>0.5?"admin":"client");
                clientRepository.save(client);
            });

            Client client1=clientRepository.findById(1L).orElse(null);
            Client client2=clientRepository.findById(2L).orElse(null);

            Abonnement abonnement1=new Abonnement();
            abonnement1.setDateAb(new Date());
            abonnement1.setTypeAb(TYPE.GSM);
            abonnement1.setSolde(1200F);
            abonnement1.setMontant(2900F);
            abonnement1.setClient(client1);
            abonnementRepository.save(abonnement1);

            Abonnement abonnement2=new Abonnement();
            abonnement2.setDateAb(new Date());
            abonnement2.setTypeAb(TYPE.INTERNET);
            abonnement2.setSolde(5000F);
            abonnement2.setMontant(100F);
            abonnement2.setClient(client2);
            abonnementRepository.save(abonnement2);


        };
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
