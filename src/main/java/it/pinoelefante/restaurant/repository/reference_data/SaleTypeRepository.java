package it.pinoelefante.restaurant.repository.reference_data;

import it.pinoelefante.restaurant.model.reference_data.SaleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleTypeRepository extends JpaRepository<SaleType, Integer> {
}
