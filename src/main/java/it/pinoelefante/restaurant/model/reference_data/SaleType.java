package it.pinoelefante.restaurant.model.reference_data;

import it.pinoelefante.restaurant.model.Identifiable;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "reference_data", name = "sale_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleType implements Identifiable<Integer> {
    @Id
    private Integer id;
    private String name;
    private String translation;
}
