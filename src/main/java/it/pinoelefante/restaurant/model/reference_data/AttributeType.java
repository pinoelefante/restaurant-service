package it.pinoelefante.restaurant.model.reference_data;

import it.pinoelefante.restaurant.model.Identifiable;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "reference_data", name = "attribute_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributeType implements Identifiable<Integer> {
    @Id
    private Integer id;
    private String name;
    private String translation;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    private List<Attribute> attributes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttributeType that = (AttributeType) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
