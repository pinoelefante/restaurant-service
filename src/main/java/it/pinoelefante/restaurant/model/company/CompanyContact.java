package it.pinoelefante.restaurant.model.company;

import it.pinoelefante.restaurant.model.Audit;
import it.pinoelefante.restaurant.model.Identifiable;
import it.pinoelefante.restaurant.model.reference_data.ContactType;
import it.pinoelefante.restaurant.repository.AuditListener;
import it.pinoelefante.restaurant.repository.Auditable;
import lombok.*;
import lombok.experimental.Delegate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "company", name = "contact")
@EntityListeners(AuditListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyContact implements Identifiable<CompanyContact.CompanyContactKey>, Auditable {
    @EmbeddedId
    @Delegate
    private CompanyContactKey id;
    private String additionalNote;
    private boolean orderContact;
    @Embedded
    private Audit audit;

    @ManyToOne
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    private Company company;
    @ManyToOne
    @JoinColumn(name = "contact_type", insertable = false, updatable = false)
    private ContactType type;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Builder
    public static class CompanyContactKey implements Serializable {
        @Column(name = "company_id")
        private Integer companyId;
        @Column(name = "contact_type")
        private Integer contactType;
        private String contact;
    }
}
