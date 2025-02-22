package net.benasker.customerfrontthymeleafapp.web;

import net.benasker.customerfrontthymeleafapp.model.Product;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;

import java.util.List;

@Controller
public class ProductController {
    @GetMapping("/products")
    public String products(Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        //  if (authentication.getAuthorities().stream()
        //        .anyMatch(auth -> !auth.getAuthority().equals("ADMIN"))) {
        //   return "redirect:/notAuthorized";  // Redirect Admins
        // }
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        DefaultOidcUser oidcUser = (DefaultOidcUser) oAuth2AuthenticationToken.getPrincipal();
        String jwtTokenValue = oidcUser.getIdToken().getTokenValue();
        RestClient restClient = RestClient.create("http://localhost:8082");
        try {
            List<Product> products = restClient.get()
                    .uri("/products")
                    .headers(httpHeaders -> httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwtTokenValue))
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            model.addAttribute("products", products);
        } catch (Exception e) {
            return "redirect:/notAuthorized";
        }
        return "products";
    }

}
