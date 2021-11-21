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
@Table(schema = "article", name = "article_price")
@EntityListeners(AuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticlePrice implements Identifiable<ArticlePrice.ArticlePriceKey>, PriceEntity, Auditable {
    @EmbeddedId
    @Delegate
    private ArticlePriceKey id;
    private BigDecimal price;
    @Embedded
    private Audit audit;
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
    public static class ArticlePriceKey implements Serializable {
        @Column(name = "article_id")
        private Integer articleId;
        private LocalDate startDate;
    }
}
