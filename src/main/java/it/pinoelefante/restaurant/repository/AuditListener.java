package it.pinoelefante.restaurant.repository;

import it.pinoelefante.restaurant.model.Audit;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AuditListener {
    @PrePersist
    public void setCreatedOn(Auditable auditable) {
        Audit audit = auditable.getAudit();

        if(audit == null) {
            audit = new Audit();
            auditable.setAudit(audit);
        }
        audit.setCreatedAt(LocalDateTime.now());
        // audit.setCreatedBy(LoggedUser.get());
    }

    @PreUpdate
    public void setUpdadtedOn(Auditable auditable) {
        Audit audit = auditable.getAudit();
        if (audit == null) {
            setCreatedOn(auditable);
        }
        audit.setUpdatedAt(LocalDateTime.now());
        // audit.setUpdatedBy(LoggedUser.get());
    }
}
