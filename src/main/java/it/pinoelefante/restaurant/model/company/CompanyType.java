package it.pinoelefante.restaurant.model.company;

import it.pinoelefante.restaurant.model.Audit;
import it.pinoelefante.restaurant.model.Identifiable;
import it.pinoelefante.restaurant.model.reference_data.CompanyTypeMeta;
import it.pinoelefante.restaurant.repository.AuditListener;
import it.pinoelefante.restaurant.repository.Auditable;
import lombok.*;
import lombok.experimental.Delegate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(schema = "company", name = "type")
@EntityListeners(AuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyType implements Identifiable<CompanyType.CompanyTypeKey>, Auditable {
    @EmbeddedId
    @Delegate
    private CompanyTypeKey id;
    private boolean enabled;
    @Embedded
    private Audit audit;

    @ManyToOne
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "type", insertable = false, updatable = false)
    private CompanyTypeMeta companyType;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class CompanyTypeKey implements Serializable {
        @Column(name = "company_id")
        private Integer companyId;
        @Column(name = "type")
        private Integer type;
        private LocalDate startDate;
    }
}
