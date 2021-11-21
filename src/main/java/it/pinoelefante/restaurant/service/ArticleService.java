package it.pinoelefante.restaurant.service;

import it.pinoelefante.restaurant.controller.dto.article.in.ArticleDtoIn;
import it.pinoelefante.restaurant.controller.dto.article.in.ArticleIngredientsDtoIn;
import it.pinoelefante.restaurant.controller.dto.article.in.ArticlePriceDtoIn;
import it.pinoelefante.restaurant.controller.dto.article.out.ArticleDtoOut;
import it.pinoelefante.restaurant.model.article.Article;
import it.pinoelefante.restaurant.model.article.ArticleAttribute;
import it.pinoelefante.restaurant.model.article.ArticleIngredient;
import it.pinoelefante.restaurant.model.article.ArticlePrice;
import it.pinoelefante.restaurant.model.reference_data.Attribute;
import it.pinoelefante.restaurant.model.reference_data.SaleType;
import it.pinoelefante.restaurant.repository.article.ArticleAttributeRepository;
import it.pinoelefante.restaurant.repository.article.ArticleIngredientsRepository;
import it.pinoelefante.restaurant.repository.article.ArticlePriceRepository;
import it.pinoelefante.restaurant.repository.article.ArticleRepository;
import it.pinoelefante.restaurant.repository.reference_data.AttributeRepository;
import it.pinoelefante.restaurant.repository.reference_data.SaleTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final AttributeRepository attributeRepository;
    private final SaleTypeRepository saleTypeRepository;
    private final ArticleAttributeRepository articleAttributeRepository;
    private final ArticlePriceRepository articlePriceRepository;
    private final ArticleIngredientsRepository articleIngredientsRepository;

    @Transactional
    public void deleteArticle(int id) {
        articleRepository.deleteById(id);
    }

    @Transactional
    public ArticleDtoOut saveArticle(ArticleDtoIn dtoId) {
        var article = isNull(dtoId.getId()) ? createArticle(dtoId) : mergeArticle(dtoId);
        return getArticle(article.getId());
    }

    private Article createArticle(ArticleDtoIn dtoIn) {
        Article article = articleRepository.save(mapArticle(dtoIn));
        mergeAttributes(article, dtoIn);
        return article;
    }

    private Article mergeArticle(ArticleDtoIn dtoIn) {
        Optional<Article> optArticle = articleRepository.findById(dtoIn.getId());
        if (optArticle.isEmpty()) {
            throw new IllegalArgumentException("Article does not exists: " + dtoIn.getId());
        }
        Article existingArticle = optArticle.get();
        existingArticle.setName(dtoIn.getName());
        mergeAttributes(existingArticle, dtoIn);
        return existingArticle;
    }

    private void mergeAttributes(Article article, ArticleDtoIn dtoIn) {
        Set<Integer> articleAttributesId = article.getAttributes().stream().map(ArticleAttribute::getAttributeId).collect(Collectors.toSet());
        Set<Integer> dtoAttributes = new HashSet<>(dtoIn.getAttributes());
        if (dtoAttributes.isEmpty()) {
            articleAttributeRepository.deleteAll(article.getAttributes());
            article.getAttributes().clear();
            return;
        }
        articleAttributesId.forEach(articleAttr -> {
            if (!dtoAttributes.contains(articleAttr)) {
                var attribute = article.getAttributes().stream().filter(x -> x.getAttributeId().equals(articleAttr)).findFirst();
                attribute.ifPresent(a -> {
                    article.getAttributes().remove(a);
                    articleAttributeRepository.delete(a);
                });
            }
        });
        dtoAttributes.forEach(dtoAttr -> {
            if (!articleAttributesId.contains(dtoAttr)) {
                Attribute attribute = attributeRepository.getById(dtoAttr);
                var key = ArticleAttribute.ArticleAttributeKey.builder().attributeId(attribute.getId()).articleId(article.getId()).build();
                var newArticleAttribute = ArticleAttribute.builder().id(key)/*.attribute(attribute).article(article)*/.build();
                var mappedAttribute = articleAttributeRepository.save(newArticleAttribute);
                article.addAttribute(mappedAttribute);
            }
        });
    }

    private Article mapArticle(ArticleDtoIn dto) {
        SaleType saleType = saleTypeRepository.getById(dto.getSaleType());
        return Article.builder()
                .enabled(true)
                .name(dto.getName())
                .saleType(saleType).build();
    }

    @Transactional
    public ArticlePrice createOrUpdatePrice(Integer articleId, ArticlePriceDtoIn priceDtoIn) {
        var existingPrice = getArticlePrice(articleId, priceDtoIn);
        if (nonNull(existingPrice)) {
            if (existingPrice.getStartDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Start date in past");
            }
            existingPrice.setPrice(priceDtoIn.getPrice());
            return existingPrice;
        } else {
            var price = mapPrice(articleId, priceDtoIn);
            return articlePriceRepository.save(price);
        }
    }

    @Transactional
    public boolean deletePrice(Integer articleId, ArticlePriceDtoIn dto) {
        var price = getArticlePrice(articleId, dto);
        if (isNull(price)) {
            return false;
        }
        return deletePrice(price);
    }

    public boolean deletePrice(ArticlePrice price) {
        if (price.getStartDate().isBefore(LocalDate.now())) {
            return false;
        }
        try {
            articlePriceRepository.delete(price);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private ArticlePrice getArticlePrice(Integer articleId, ArticlePriceDtoIn dto) {
        var key = ArticlePrice.ArticlePriceKey.builder().articleId(articleId).startDate(dto.getStartDate()).build();
        return articlePriceRepository.findById(key).orElse(null);
    }

    private ArticlePrice mapPrice(Integer articleId, ArticlePriceDtoIn dto) {
        var key = ArticlePrice.ArticlePriceKey.builder().articleId(articleId).startDate(dto.getStartDate()).build();
        ArticlePrice price = ArticlePrice.builder().id(key).price(dto.getPrice()).build();
        return price;
    }

    @Transactional
    public ArticleDtoOut createOrUpdateArticleIngredients(Integer articleId, ArticleIngredientsDtoIn dto) {
        var ingredients = getArticleIngredients(articleId);
        mergeIngredients(articleId, dto, ingredients);
        return getArticle(articleId);
    }

    private List<ArticleIngredient> getArticleIngredients(Integer articleId) {
        try {
            return articleIngredientsRepository.getArticleIngredientsByArticleId(articleId);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public void mergeIngredients(Integer articleId, ArticleIngredientsDtoIn dto, List<ArticleIngredient> current) {
        if (isNull(dto) || dto.getIngredients().isEmpty()) {
            articleIngredientsRepository.deleteAllByArticleId(articleId);
            return;
        }
        dto.getIngredients().forEach(newIngredient -> {
            var optCurr = current.stream().filter(ingr -> ingr.getIngredientId().equals(newIngredient.getIngredientId())).findFirst();
            optCurr.ifPresentOrElse(currentIngredient -> {
                if (!currentIngredient.getOrder().equals(newIngredient.getOrder())) {
                    currentIngredient.setOrder(newIngredient.getOrder());
                }
            }, () -> {
                var key = ArticleIngredient.ArticleIngredientKey.builder().articleId(articleId).ingredientId(newIngredient.getIngredientId()).build();
                var newArticleIngredient = ArticleIngredient.builder().id(key).order(newIngredient.getOrder()).build();
                articleIngredientsRepository.save(newArticleIngredient);
            });
        });

        current.forEach(currIngr -> {
            var isInDto = dto.getIngredients().stream().filter(x -> x.getIngredientId().equals(currIngr.getIngredientId())).findFirst();
            if (isInDto.isEmpty()) {
                articleIngredientsRepository.deleteById(currIngr.getId());
            }
        });
    }

    @Transactional
    public ArticleDtoOut getArticle(Integer articleId) {
        return articleRepository.getArticleDto(articleId);
    }

    @Transactional
    public List<ArticleDtoOut> getAllArticles(boolean includeDisabled) {
        var articles = includeDisabled ? articleRepository.getAllArticles() : articleRepository.getActiveArticles();
        return articles.stream().map(ArticleDtoOut::new).collect(Collectors.toList());
    }
}
