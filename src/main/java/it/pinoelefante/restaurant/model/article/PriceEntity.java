package it.pinoelefante.restaurant.model.article;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface PriceEntity {
    LocalDate getStartDate();
    BigDecimal getPrice();
}
