package it.pinoelefante.restaurant.controller.dto.article.out;

import it.pinoelefante.restaurant.model.reference_data.Country;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CountryDtoOut implements Serializable {
    private String code;
    private String description;

    public CountryDtoOut(Country country) {
        setCode(country.getCode());
        setDescription(country.getTranslation());
    }
}
