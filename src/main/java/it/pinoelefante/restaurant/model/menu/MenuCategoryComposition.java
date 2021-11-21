package it.pinoelefante.restaurant.model.menu;

import it.pinoelefante.restaurant.model.Audit;
import it.pinoelefante.restaurant.model.Identifiable;
import it.pinoelefante.restaurant.model.article.Article;
import it.pinoelefante.restaurant.repository.AuditListener;
import it.pinoelefante.restaurant.repository.Auditable;
import lombok.*;
import lombok.experimental.Delegate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "menu", name = "category_composition")
@EntityListeners(AuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuCategoryComposition implements Identifiable<MenuCategoryComposition.MenuCategoryCompositionKey>, Auditable {
    @EmbeddedId
    @Delegate
    private MenuCategoryCompositionKey id;
    private Integer order;
    @Embedded
    private Audit audit;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "menu_id", insertable = false, updatable = false),
            @JoinColumn(name = "category_id", insertable = false, updatable = false)
    })
    private MenuComposition menuComposition;

    @ManyToOne
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private Article article;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class MenuCategoryCompositionKey implements Serializable {
        @Column(name = "menu_id")
        private Integer menuId;
        @Column(name = "category_id")
        private Integer categoryId;
        @Column(name = "article_id")
        private Integer articleId;
    }
}
