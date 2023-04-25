package ma.enset.subscription_mangement_system.web;


import jakarta.validation.Valid;
import ma.enset.subscription_mangement_system.entities.Abonnement;
import ma.enset.subscription_mangement_system.entities.Client;
import ma.enset.subscription_mangement_system.repositories.AbonnementRepository;
import ma.enset.subscription_mangement_system.repositories.ClientRepository;
import ma.enset.subscription_mangement_system.service.IAbonnementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller
public class AbonnementController {
    private IAbonnementService abonnementService;
    private AbonnementRepository abonnementRepository;

    public AbonnementController(IAbonnementService abonnementService, AbonnementRepository abonnementRepository, ClientRepository clientRepository) {
        this.abonnementService = abonnementService;
        this.abonnementRepository = abonnementRepository;
        this.clientRepository = clientRepository;
    }

    private ClientRepository clientRepository;





    @GetMapping("/abonnements/{id}")
    public String listAbonnements(Model model, @PathVariable Long id) {
        List<Abonnement> abonnements = abonnementService.getByClient_Id(id);
        model.addAttribute("abonnements", abonnements);
        model.addAttribute("id",id);
        return "abonnements";
    }
    @GetMapping("/abonnements/new/{id}")
    public String createAbonnementForm(Model model,@PathVariable long id) {
        Abonnement abonnement = new Abonnement();
        model.addAttribute("abonnement", abonnement);
        model.addAttribute("id",id);
        return "create_abonnement";

    }
    /*@GetMapping("/abonnements")
    public String listAbonnements(Model model) {
        List<Abonnement> abonnements = abonnementService.getAllAbonnements();
        model.addAttribute("abonnements", abonnements);
        return "abonnements";
    }*/
    @PostMapping("/abonnements")
    public String saveAbonnement(@Valid Abonnement abonnement, Model model, @RequestParam Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new IllegalArgumentException("Invalid client Id:" + clientId));
        abonnement.setClient(client);
        model.addAttribute("abonnement", abonnement);
        abonnementService.saveAbonnement(abonnement);
       return "redirect:/abonnements/"+clientId;
    }

    @GetMapping("/abonnements/edit/{id}")
    public String editAbonnementForm(@PathVariable Long id, Model model,@RequestParam(value = "clientId") long clientId) {
        model.addAttribute("abonnement", abonnementService.getAbonnementById(id));
        model.addAttribute("clientId",clientId);
        return "edit_abonnement";
    }
    @PostMapping("/abonnements/{id}")
    public String updateAbonnement(@PathVariable Long id,
                                   @Valid Abonnement abonnement,
                                   Model model,@RequestParam long clientId) {
        model.addAttribute("abonnement", abonnement);
        Abonnement existingabonnement = abonnementService.getAbonnementById(id);
        existingabonnement.setId(id);
        existingabonnement.setTypeAb(abonnement.getTypeAb());
        existingabonnement.setDateAb(abonnement.getDateAb());
        existingabonnement.setSolde(abonnement.getSolde());
        existingabonnement.setMontant(abonnement.getMontant());
        abonnementService.updateAbonnement(existingabonnement);
        return "redirect:/abonnements/"+clientId;
    }
    @GetMapping("/deleteAbonnement/{id}")
    public String deleteAbonnement(@PathVariable Long id,@RequestParam(value = "clientId") long clientId) {
        abonnementService.deleteAbonnementById(id);
        return "redirect:/abonnements/"+clientId;
    }
}
