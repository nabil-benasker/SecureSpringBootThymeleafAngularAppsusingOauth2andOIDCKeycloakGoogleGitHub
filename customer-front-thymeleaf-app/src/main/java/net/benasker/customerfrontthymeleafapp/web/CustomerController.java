package net.benasker.customerfrontthymeleafapp.web;

import net.benasker.customerfrontthymeleafapp.entities.Customer;
import net.benasker.customerfrontthymeleafapp.repository.CustomerRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {
    private CustomerRepository customerRepository;
    ClientRegistrationRepository clientRegistrationRepository;

     public CustomerController(CustomerRepository customerRepository,ClientRegistrationRepository clientRegistrationRepository) {
        this.customerRepository = customerRepository;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @GetMapping("/customers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String customers(Model model) {
        List<Customer> customersList = customerRepository.findAll();
        model.addAttribute("customers", customersList);
        return "customers";
    }



    @GetMapping("/auth")
    @ResponseBody
    public Authentication authentication(Authentication authentication) {
        return authentication;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/notAuthorized")
    public String notAuthorized() {
        return "notAuthorized";
    }
    @GetMapping("/oauth2Login")
    public String oauth2login(Model model){
        String authorizationRequestUri="oauth2/authorization";
        Map<String,String> oauth2AuthenticationUrls=new HashMap<>();
        Iterable<ClientRegistration> clientRegistrations= (Iterable<ClientRegistration>) clientRegistrationRepository;
        clientRegistrations.forEach(registration->{
            oauth2AuthenticationUrls.put(registration.getClientName(),authorizationRequestUri+"/"+registration.getRegistrationId());

        });
        model.addAttribute("urls",oauth2AuthenticationUrls);
        return "oauth2Login";
    }
}
