package it.pinoelefante.restaurant.model.article;

import it.pinoelefante.restaurant.model.Audit;
import it.pinoelefante.restaurant.model.Identifiable;
import it.pinoelefante.restaurant.repository.AuditListener;
import it.pinoelefante.restaurant.repository.Auditable;
import lombok.*;
import lombok.experimental.Delegate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "article", name = "article_ingredient")
@EntityListeners(AuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleIngredient implements Identifiable<ArticleIngredient.ArticleIngredientKey>, Auditable {
    @EmbeddedId
    @Delegate
    private ArticleIngredientKey id;
    private Integer order;
    @Embedded
    private Audit audit;

    @ManyToOne
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private Article article;
    @ManyToOne
    @JoinColumn(name = "ingredient_id", insertable = false, updatable = false)
    private Ingredient ingredient;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class ArticleIngredientKey implements Serializable {
        @Column(name = "article_id")
        private Integer articleId;
        @Column(name = "ingredient_id")
        private Integer ingredientId;
    }
}
