package it.pinoelefante.restaurant.repository.article;

import it.pinoelefante.restaurant.model.article.ArticlePrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticlePriceRepository extends JpaRepository<ArticlePrice, ArticlePrice.ArticlePriceKey> {
}
