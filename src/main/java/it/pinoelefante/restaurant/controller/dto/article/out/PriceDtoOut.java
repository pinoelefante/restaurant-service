package it.pinoelefante.restaurant.controller.dto.article.out;

import it.pinoelefante.restaurant.model.article.PriceEntity;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PriceDtoOut implements Serializable {
    private BigDecimal price;
    private LocalDate startDate;

    public PriceDtoOut(PriceEntity p) {
        price = p.getPrice();
        startDate = p.getStartDate();
    }
}
