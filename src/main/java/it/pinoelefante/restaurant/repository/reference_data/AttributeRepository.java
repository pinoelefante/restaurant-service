package it.pinoelefante.restaurant.repository.reference_data;

import it.pinoelefante.restaurant.model.reference_data.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
}
