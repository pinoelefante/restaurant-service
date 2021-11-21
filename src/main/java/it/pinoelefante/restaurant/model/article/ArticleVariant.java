package it.pinoelefante.restaurant.model.article;

import it.pinoelefante.restaurant.model.Audit;
import it.pinoelefante.restaurant.model.Identifiable;
import it.pinoelefante.restaurant.repository.AuditListener;
import it.pinoelefante.restaurant.repository.Auditable;
import lombok.*;
import lombok.experimental.Delegate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static java.util.Objects.isNull;

@Entity
@Table(schema = "article", name = "article_variant")
@EntityListeners(AuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleVariant implements Identifiable<ArticleVariant.ArticleVariantKey>, EntityWithPrice, Auditable {
    @EmbeddedId
    @Delegate
    private ArticleVariantKey id;
    @Embedded
    private Audit audit;

    @ManyToOne
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private Article article;
    @ManyToOne
    @JoinColumn(name = "variant_id", insertable = false, updatable = false)
    private Variant variant;

    @OneToMany(mappedBy = "articleVariant", fetch = FetchType.LAZY)
    private List<ArticleVariantPrice> variantPrices;

    public PriceEntity getCurrentPrice() {
        if (isNull(variantPrices) || variantPrices.isEmpty()) {
            return null;
        }
        return variantPrices.stream()
                .filter(x -> x.getStartDate().compareTo(LocalDate.now()) <= 0)
                .max(Comparator.comparing(ArticleVariantPrice::getStartDate))
                .stream().findFirst().orElse(null);
    }

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class ArticleVariantKey implements Serializable {
        @Column(name = "article_id")
        private Integer articleId;
        @Column(name = "variant_id")
        private Integer variantId;
    }
}
