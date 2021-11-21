package it.pinoelefante.restaurant.model.reference_data;

import it.pinoelefante.restaurant.model.Identifiable;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(schema = "reference_data", name = "attribute")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attribute implements Identifiable<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String translation;
    private String symbol;

    @ManyToOne
    @JoinColumn(name = "type")
    private AttributeType type;
}
