package it.pinoelefante.restaurant.controller.dto.article.out;

import it.pinoelefante.restaurant.model.company.Company;
import lombok.*;

import java.io.Serializable;

import static java.util.Objects.nonNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CompanyDtoOut implements Serializable {
    private Integer id;
    private String name;
    private String vatNumber;
    private String address;
    private CountryDtoOut country;

    public CompanyDtoOut(Company comp) {
        setId(comp.getId());
        setName(comp.getName());
        setVatNumber(comp.getVatNumber());
        setAddress(comp.getAddress());
        if (nonNull(comp.getCountry())) {
            setCountry(new CountryDtoOut(comp.getCountry()));
        }
    }
}
