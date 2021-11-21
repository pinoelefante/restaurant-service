package it.pinoelefante.restaurant.repository.article;

import it.pinoelefante.restaurant.model.article.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}
