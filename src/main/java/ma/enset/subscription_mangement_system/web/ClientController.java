package ma.enset.subscription_mangement_system.web;

import jakarta.validation.Valid;
import ma.enset.subscription_mangement_system.entities.Abonnement;
import ma.enset.subscription_mangement_system.entities.Client;
import ma.enset.subscription_mangement_system.repositories.AbonnementRepository;
import ma.enset.subscription_mangement_system.repositories.ClientRepository;
import ma.enset.subscription_mangement_system.service.IAbonnementService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClientController {
    private IAbonnementService clientService;
    private ClientRepository clientRepository;

    public ClientController(IAbonnementService clientService, ClientRepository clientRepository) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    // handler method to handle list clients and return mode and view
    @GetMapping("/clients")
    public String listClients(Model model,@RequestParam(name = "page",defaultValue = "0") int page,
                              @RequestParam(name = "size",defaultValue = "8") int size,
                              @RequestParam(name = "keyword",defaultValue = "") String kw) {
        Page<Client> pageClients = clientRepository.findByNomContains(kw, PageRequest.of(page,size));
        model.addAttribute("clients", pageClients.getContent());
        model.addAttribute("pages",new int[pageClients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        return "clients";
    }

    @GetMapping("/clients/new")
    public String createClientForm(Model model) {

        // create client object to hold client form data
        Client client = new Client();
        model.addAttribute("client", client);
        return "create_client";

    }

    @PostMapping("/clients")
    public String saveClient(@Valid Client client,Model model) {
        model.addAttribute("client",client);
        clientService.saveClient(client);
        return "redirect:/clients";
    }

    @GetMapping("/clients/edit/{id}")
    public String editClientForm(@PathVariable Long id, Model model) {
        model.addAttribute("client", clientService.getClientById(id));
        return "edit_client";
    }

    @PostMapping("/clients/{id}")
    public String updateClient(@PathVariable Long id,
                               @Valid Client client,
                                Model model) {
        model.addAttribute("client",client);
        // get client from database by id
        @Valid
        Client existingClient = clientService.getClientById(id);
        existingClient.setId(id);
        existingClient.setNom(client.getNom());
        existingClient.setEmail(client.getEmail());
        existingClient.setUsername(client.getUsername());

        // save updated client object
        clientService.updateClient(existingClient);
        return "redirect:/clients";
    }

    // handler method to handle delete client request

    @GetMapping("/clients/{id}")
    public String deleteClient(@PathVariable Long id,String keyword, int page) {
        clientService.deleteClientById(id);
        return "redirect:/clients?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/")
    public String home(){
        return "redirect:/clients";
    }
}
