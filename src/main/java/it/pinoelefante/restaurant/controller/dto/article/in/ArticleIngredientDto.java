package it.pinoelefante.restaurant.controller.dto.article.in;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@Getter
public class ArticleIngredientDto implements Serializable {
    @NotNull
    @Min(value = 1)
    private Integer ingredientId;
    private int order;
}
