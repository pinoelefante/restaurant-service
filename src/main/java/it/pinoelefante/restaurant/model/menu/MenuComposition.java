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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Entity
@Table(schema = "menu", name = "composition")
@EntityListeners(AuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuComposition implements Identifiable<MenuComposition.MenuCompositionKey>, Auditable {
    @EmbeddedId
    @Delegate
    private MenuCompositionKey id;
    private Integer order;
    @Embedded
    private Audit audit;

    @ManyToOne
    @JoinColumn(name = "menu_id", insertable = false, updatable = false)
    private Menu menu;
    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private MenuCategory category;

    @OneToMany(mappedBy = "menuComposition")
    private List<MenuCategoryComposition> menuComposition;

    public List<Article> getArticles() {
        if (isNull(menuComposition) || menuComposition.isEmpty()) {
            return Collections.emptyList();
        }
        return menuComposition.stream()
                .sorted(Comparator.comparing(MenuCategoryComposition::getOrder).thenComparing(x -> x.getAudit().getCreatedAt()))
                .map(MenuCategoryComposition::getArticle).collect(Collectors.toList());
    }

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class MenuCompositionKey implements Serializable {
        @Column(name = "menu_id")
        private Integer menuId;
        @Column(name = "category_id")
        private Integer categoryId;
    }
}
