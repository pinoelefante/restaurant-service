package it.pinoelefante.restaurant.controller.dto.article.out;

import it.pinoelefante.restaurant.model.article.Article;
import it.pinoelefante.restaurant.model.article.ArticleAttribute;
import it.pinoelefante.restaurant.model.article.ArticleIngredient;
import it.pinoelefante.restaurant.model.article.PriceEntity;
import lombok.*;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ArticleDtoOut implements Serializable {
    private Integer articleId;
    private String name;
    private SaleTypeDtoOut saleType;
    private List<ArticleAttributeTypeDtoOut> attributeTypes;
    private List<ArticleIngredientDtoOut> ingredients;
    private PriceDtoOut prevPrice;
    private PriceDtoOut price;
    private PriceDtoOut nextPrice;
    private boolean visible;

    public ArticleDtoOut(Article article) {
        setArticleId(article.getId());
        setName(article.getName());
        setSaleType(new SaleTypeDtoOut(article.getSaleType()));
        setVisible(article.isEnabled());
        populateIngredients(article.getIngredients());
        populateAttributes(article.getAttributes());
        populatePrices(article);
    }

    private void populateIngredients(List<ArticleIngredient> ingrs) {
        if (isNull(ingrs) || ingrs.isEmpty()) {
            this.ingredients = Collections.emptyList();
            return;
        }
        this.ingredients = ingrs.stream().sorted(Comparator.comparing(ArticleIngredient::getOrder).thenComparing(x -> x.getAudit().getCreatedAt()))
                .map(ArticleIngredientDtoOut::new)
                .collect(Collectors.toList());
    }

    private void populateAttributes(List<ArticleAttribute> attributes) {
        var attrByType = attributes.stream()
                .collect(Collectors.groupingBy(x -> x.getAttribute().getType()))
                .entrySet()
                .stream().map(e -> new ArticleAttributeTypeDtoOut(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
        this.attributeTypes = attrByType;
    }

    private void populatePrices(Article article) {
        this.price = getPriceDto(article.getCurrentPrice());
        this.prevPrice = getPriceDto(article.getPreviousPrice());
        this.nextPrice = getPriceDto(article.getNextPrice());
    }

    private PriceDtoOut getPriceDto(PriceEntity p) {
        return nonNull(p) ? new PriceDtoOut(p) : null;
    }
}
