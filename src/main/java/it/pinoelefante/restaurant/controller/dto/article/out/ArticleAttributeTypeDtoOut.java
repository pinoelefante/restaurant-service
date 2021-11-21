package it.pinoelefante.restaurant.controller.dto.article.out;

import it.pinoelefante.restaurant.model.article.ArticleAttribute;
import it.pinoelefante.restaurant.model.reference_data.AttributeType;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ArticleAttributeTypeDtoOut implements Serializable {
    private Integer type;
    private String description;
    private List<ArticleAttributeDtoOut> attributes;

    public ArticleAttributeTypeDtoOut(AttributeType type, List<ArticleAttribute> attributes) {
        setType(type.getId());
        var descr = StringUtils.firstNonBlank(type.getTranslation(), type.getName());
        setDescription(descr);
        var sorted = attributes.stream().sorted(Comparator.comparing(ArticleAttribute::getAttributeId)).collect(Collectors.toList());
        var dtos = attributes.stream().map(ArticleAttributeDtoOut::new).collect(Collectors.toList());
        setAttributes(dtos);
    }
}
