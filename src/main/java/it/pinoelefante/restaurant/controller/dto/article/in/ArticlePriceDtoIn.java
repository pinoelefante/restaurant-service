package it.pinoelefante.restaurant.controller.dto.article.in;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class ArticlePriceDtoIn implements Serializable {
    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    @Digits(integer = 9, fraction = 2)
    private BigDecimal price;
    @NotNull
    @FutureOrPresent
    private LocalDate startDate;
}
