package it.pinoelefante.restaurant.repository.article;

import it.pinoelefante.restaurant.model.article.ArticleIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleIngredientsRepository extends JpaRepository<ArticleIngredient, ArticleIngredient.ArticleIngredientKey> {
    @Query("SELECT i from ArticleIngredient i where i.id.articleId = ?1")
    List<ArticleIngredient> getArticleIngredientsByArticleId(Integer articleId);
    @Query("delete from ArticleIngredient ai where ai.id.articleId = ?1")
    void deleteAllByArticleId(Integer articleId);
}
