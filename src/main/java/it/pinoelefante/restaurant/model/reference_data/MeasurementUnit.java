package it.pinoelefante.restaurant.model.reference_data;

import it.pinoelefante.restaurant.model.Identifiable;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "reference_data", name = "measurement_unit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeasurementUnit implements Identifiable<String> {
    @Id
    @Length(min = 1, max = 2)
    private String code;
    private boolean weight;

    @Override
    public String getId() {
        return getCode();
    }
}
