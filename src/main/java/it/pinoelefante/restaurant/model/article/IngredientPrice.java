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
@Table(schema = "article", name = "ingredient_price")
@EntityListeners(AuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientPrice implements Identifiable<IngredientPrice.IngredientPriceKey>, PriceEntity, Auditable {
    @EmbeddedId
    @Delegate
    private IngredientPriceKey id;
    private BigDecimal price;
    @Embedded
    private Audit audit;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class IngredientPriceKey implements Serializable {
        @Column(name = "ingredient_id")
        private Integer ingredient_id;
        private LocalDate startDate;
    }
}
