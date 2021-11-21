package it.pinoelefante.restaurant.repository.article;

import it.pinoelefante.restaurant.model.article.ArticleAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleAttributeRepository extends JpaRepository<ArticleAttribute, ArticleAttribute.ArticleAttributeKey> {
}
