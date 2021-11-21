package it.pinoelefante.restaurant.model.article;

import it.pinoelefante.restaurant.model.Audit;
import it.pinoelefante.restaurant.model.Identifiable;
import it.pinoelefante.restaurant.model.reference_data.MeasurementUnit;
import it.pinoelefante.restaurant.repository.AuditListener;
import it.pinoelefante.restaurant.repository.Auditable;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(schema = "article", name = "variant")
@EntityListeners(AuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Variant implements Identifiable<Integer>, Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer weight;
    private String name;
    private String translation;
    @Embedded
    private Audit audit;

    @ManyToOne
    @JoinColumn(name = "unit_code")
    private MeasurementUnit unit;
}
