package it.pinoelefante.restaurant.model.article;

import it.pinoelefante.restaurant.model.Audit;
import it.pinoelefante.restaurant.model.Identifiable;
import it.pinoelefante.restaurant.model.reference_data.Attribute;
import it.pinoelefante.restaurant.repository.AuditListener;
import it.pinoelefante.restaurant.repository.Auditable;
import lombok.*;
import lombok.experimental.Delegate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "article", name = "article_attribute")
@EntityListeners(AuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleAttribute implements Identifiable<ArticleAttribute.ArticleAttributeKey>, Auditable {
    @EmbeddedId
    @Delegate
    private ArticleAttributeKey id;
    @Embedded
    private Audit audit;

    @ManyToOne
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private Article article;
    @ManyToOne
    @JoinColumn(name = "attribute_id", insertable = false, updatable = false)
    private Attribute attribute;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class ArticleAttributeKey implements Serializable {
        @Column(name = "article_id")
        private Integer articleId;
        @Column(name = "attribute_id")
        private Integer attributeId;
    }
}
