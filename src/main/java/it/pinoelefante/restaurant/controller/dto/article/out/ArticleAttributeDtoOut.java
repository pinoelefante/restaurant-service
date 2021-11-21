package it.pinoelefante.restaurant.controller.dto.article.out;

import it.pinoelefante.restaurant.model.article.ArticleAttribute;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ArticleAttributeDtoOut implements Serializable {
    private Integer id;
    private String description;
    private String symbol;

    public ArticleAttributeDtoOut(ArticleAttribute attribute) {
        setId(attribute.getAttributeId());
        var attr = attribute.getAttribute();
        var descr = StringUtils.firstNonBlank(attr.getTranslation(), attr.getName());
        setDescription(descr);
        setSymbol(attr.getSymbol());
    }
}
