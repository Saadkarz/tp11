package ma.rest.spring;

import ma.rest.spring.entities.Client;
import ma.rest.spring.entities.Compte;
import ma.rest.spring.entities.TypeCompte;
import ma.rest.spring.repositories.ClientRepository;
import ma.rest.spring.repositories.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.Date;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner start(CompteRepository compteRepository, ClientRepository clientRepository, RepositoryRestConfiguration restConfiguration) {
		return args -> {
			// Expose IDs for Compte and Client entities
			restConfiguration.exposeIdsFor(Compte.class);
			restConfiguration.exposeIdsFor(Client.class);

			// Create and save clients
			Client c1 = clientRepository.save(new Client(null, "Amal", "amal@example.com", null));
			Client c2 = clientRepository.save(new Client(null, "Ali", "ali@example.com", null));

			// Create and save accounts associated with clients
			compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE, c1));
			compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.COURANT, c1));
			compteRepository.save(new Compte(null, Math.random() * 9000, new Date(), TypeCompte.EPARGNE, c2));

			// Display all accounts
			compteRepository.findAll().forEach(c -> {
				System.out.println(c.toString());
			});
		};
	}

}
