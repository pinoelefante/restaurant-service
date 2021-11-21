package it.pinoelefante.restaurant.repository.article;

import it.pinoelefante.restaurant.controller.dto.article.out.ArticleDtoOut;
import it.pinoelefante.restaurant.model.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import static java.util.Objects.isNull;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    @Query("SELECT a from Article a where a.enabled = true order by coalesce(a.audit.updatedAt, a.audit.createdAt) desc")
    List<Article> getActiveArticles();

    @Query("SELECT a from Article a order by coalesce(a.audit.updatedAt, a.audit.createdAt) desc")
    List<Article> getAllArticles();

    @Query("SELECT a from Article a where a.enabled = false")
    List<Article> getDisabledArticles();

    default ArticleDtoOut getArticleDto(Integer articleId) {
        if (isNull(articleId) || articleId <= 0) {
            return null;
        }
        var article = findById(articleId).orElse(null);
        if (isNull(article)) {
            return null;
        }
        return new ArticleDtoOut(article);
    }
}
