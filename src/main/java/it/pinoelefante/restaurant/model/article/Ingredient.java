package it.pinoelefante.restaurant.model.article;

import it.pinoelefante.restaurant.model.Audit;
import it.pinoelefante.restaurant.model.Identifiable;
import it.pinoelefante.restaurant.model.company.Company;
import it.pinoelefante.restaurant.model.reference_data.Country;
import it.pinoelefante.restaurant.repository.AuditListener;
import it.pinoelefante.restaurant.repository.Auditable;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "article", name = "ingredient")
@EntityListeners(AuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredient implements Identifiable<Integer>, Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Embedded
    private Audit audit;

    @ManyToOne
    @JoinColumn(name = "origin_country")
    private Country originCountry;
    @ManyToOne
    @JoinColumn(name = "manufacturer")
    private Company manufacturer;

    @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY)
    private List<IngredientAttribute> attributes;
}
