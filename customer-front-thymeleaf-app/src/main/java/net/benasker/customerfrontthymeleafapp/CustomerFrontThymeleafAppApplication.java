package net.benasker.customerfrontthymeleafapp;

import net.benasker.customerfrontthymeleafapp.entities.Customer;
import net.benasker.customerfrontthymeleafapp.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerFrontThymeleafAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerFrontThymeleafAppApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(CustomerRepository customerRepository){
		return args->{
			customerRepository.save(Customer.builder().
					name("Nabil").
					email("nabil.benasker@gmail.com").
					build());
			customerRepository.save(Customer.builder().
					name("Beya").
					email("beya.benasker@gmail.com").
					build());
			customerRepository.save(Customer.builder().
					name("Sofia").
					email("sofia.benasker@gmail.com").
					build());
			customerRepository.save(Customer.builder().
					name("Hela").
					email("hela.benasker@gmail.com").
					build());
		};
	}

}
