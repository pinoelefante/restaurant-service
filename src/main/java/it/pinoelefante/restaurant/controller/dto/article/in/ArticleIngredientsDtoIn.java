package it.pinoelefante.restaurant.controller.dto.article.in;

import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.nonNull;

@NoArgsConstructor
public class ArticleIngredientsDtoIn implements Serializable {
    private List<ArticleIngredientDto> ingredients;

    public List<ArticleIngredientDto> getIngredients() {
        return nonNull(ingredients) ? ingredients : Collections.emptyList();
    }
}
