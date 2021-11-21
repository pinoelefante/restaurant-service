package it.pinoelefante.restaurant.controller.dto.article.out;

import it.pinoelefante.restaurant.model.article.ArticleIngredient;
import it.pinoelefante.restaurant.model.article.Ingredient;
import lombok.*;

import java.io.Serializable;
import java.util.List;

import static java.util.Objects.isNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ArticleIngredientDtoOut implements Serializable {
    private Integer id;
    private String description;
    private CountryDtoOut country;
    private CompanyDtoOut manufacturer;
    private List<ArticleAttributeDtoOut> attributes;
    private Integer order;

    public ArticleIngredientDtoOut(ArticleIngredient articleIngredient) {
        setId(articleIngredient.getIngredientId());
        var descr = articleIngredient.getIngredient().getName();
        setDescription(descr);
        populateCountry(articleIngredient.getIngredient());
        populateManufacturer(articleIngredient.getIngredient());
        setOrder(articleIngredient.getOrder());
    }

    private void populateCountry(Ingredient ingr) {
        if (isNull(ingr.getOriginCountry())) {
            return;
        }
        var country = ingr.getOriginCountry();
        setCountry(new CountryDtoOut(country));
    }

    private void populateManufacturer(Ingredient ingr) {
        if (isNull(ingr.getManufacturer())) {
            return;
        }
        setManufacturer(new CompanyDtoOut(ingr.getManufacturer()));
    }
}
