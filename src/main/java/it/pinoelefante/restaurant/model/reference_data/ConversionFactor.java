package it.pinoelefante.restaurant.model.reference_data;

import it.pinoelefante.restaurant.model.Identifiable;
import lombok.*;
import lombok.experimental.Delegate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "reference_data", name = "conversion_factor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConversionFactor implements Identifiable<ConversionFactor.ConversionFactorKey> {
    @EmbeddedId
    @Delegate
    private ConversionFactorKey id;
    private int factor;

    @ManyToOne
    @JoinColumn(name = "code", insertable = false, updatable = false)
    private MeasurementUnit unit;
    @ManyToOne
    @JoinColumn(name = "base_code", insertable = false, updatable = false)
    private MeasurementUnit baseUnit;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public class ConversionFactorKey implements Serializable {
        @Column(name = "code")
        private String code;
        @Column(name = "base_code")
        private String baseCode;
    }

}
