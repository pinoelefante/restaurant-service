package it.pinoelefante.restaurant.repository;

import it.pinoelefante.restaurant.model.Audit;

public interface Auditable {
    Audit getAudit();
    void setAudit(Audit a);
}
