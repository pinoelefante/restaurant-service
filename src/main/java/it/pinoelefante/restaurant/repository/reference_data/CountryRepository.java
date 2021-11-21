package it.pinoelefante.restaurant.repository.reference_data;

import it.pinoelefante.restaurant.model.reference_data.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
