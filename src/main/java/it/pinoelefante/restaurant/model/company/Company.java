package it.pinoelefante.restaurant.model.company;

import it.pinoelefante.restaurant.model.Audit;
import it.pinoelefante.restaurant.model.Identifiable;
import it.pinoelefante.restaurant.model.reference_data.Country;
import it.pinoelefante.restaurant.repository.AuditListener;
import it.pinoelefante.restaurant.repository.Auditable;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "company", name = "company")
@EntityListeners(AuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company implements Identifiable<Integer>, Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String vatNumber;
    private String address;
    @Embedded
    private Audit audit;

    @ManyToOne
    @JoinColumn(name = "country")
    private Country country;

    @OneToMany(mappedBy = "company")
    private List<CompanyType> companyTypes;
}
