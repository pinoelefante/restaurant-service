package it.pinoelefante.restaurant.model.article;

import it.pinoelefante.restaurant.model.Identifiable;
import it.pinoelefante.restaurant.model.reference_data.Attribute;
import it.pinoelefante.restaurant.repository.AuditListener;
import lombok.*;
import lombok.experimental.Delegate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "article", name = "ingredient_attribute")
@EntityListeners(AuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientAttribute implements Identifiable<IngredientAttribute.IngredientAttributeKey> {
    @EmbeddedId
    @Delegate
    private IngredientAttributeKey id;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", insertable = false, updatable = false)
    private Ingredient ingredient;
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
    public static class IngredientAttributeKey implements Serializable {
        @Column(name = "ingredient_id")
        private Integer ingredientId;
        @Column(name = "attribute_id")
        private Integer attributeId;
    }
}
