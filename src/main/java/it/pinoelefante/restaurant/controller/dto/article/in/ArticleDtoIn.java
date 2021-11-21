package it.pinoelefante.restaurant.controller.dto.article.in;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.nonNull;

@Getter
@NoArgsConstructor
public class ArticleDtoIn implements Serializable {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    @Min(value = 1)
    @Max(value = 3)
    private Integer saleType;
    private List<Integer> attributes;

    public List<Integer> getAttributes() {
        return nonNull(attributes) ? attributes : Collections.emptyList();
    }
}
