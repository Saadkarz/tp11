package ma.rest.spring.entities;

import ma.rest.spring.entities.Compte;
import ma.rest.spring.entities.TypeCompte;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "solde", types = Compte.class)
public interface CompteProjection1 {
    double getSolde();
}

