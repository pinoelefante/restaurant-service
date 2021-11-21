package it.pinoelefante.restaurant.model.article;

import it.pinoelefante.restaurant.model.Audit;
import it.pinoelefante.restaurant.model.Identifiable;
import it.pinoelefante.restaurant.repository.AuditListener;
import it.pinoelefante.restaurant.repository.Auditable;
import lombok.*;
import lombok.experimental.Delegate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(schema = "article", name = "article_variant_price")
@EntityListeners(AuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleVariantPrice implements Identifiable<ArticleVariantPrice.ArticleVariantPriceKey>, PriceEntity, Auditable {
    @EmbeddedId
    @Delegate
    private ArticleVariantPriceKey id;
    private BigDecimal price;
    @Embedded
    private Audit audit;
    @ManyToOne
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private Article article;
    @ManyToOne
    @JoinColumn(name = "variant_id", insertable = false, updatable = false)
    private Variant variant;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "article_id", insertable = false, updatable = false),
            @JoinColumn(name = "variant_id", insertable = false, updatable = false),
    })
    private ArticleVariant articleVariant;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class ArticleVariantPriceKey implements Serializable {
        @Column(name = "article_id")
        private Integer articleId;
        @Column(name = "variant_id")
        private Integer variantId;
        private LocalDate startDate;
    }
}
