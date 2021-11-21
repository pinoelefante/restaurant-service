package it.pinoelefante.restaurant.model.reference_data;

import it.pinoelefante.restaurant.model.Identifiable;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "reference_data", name = "contact_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactType implements Identifiable<Integer> {
    @Id
    private Integer id;
    private String name;
    private String translation;
    private boolean visible;
}
