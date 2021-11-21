package it.pinoelefante.restaurant.controller;

import it.pinoelefante.restaurant.controller.dto.article.in.ArticleDtoIn;
import it.pinoelefante.restaurant.controller.dto.article.in.ArticleIngredientsDtoIn;
import it.pinoelefante.restaurant.controller.dto.article.in.ArticlePriceDtoIn;
import it.pinoelefante.restaurant.controller.dto.article.out.ArticleDtoOut;
import it.pinoelefante.restaurant.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping
    public List<ArticleDtoOut> getAllArticles(@RequestParam(name = "include_disabled", required = false, defaultValue = "false") boolean includeDisabled) {
        return articleService.getAllArticles(includeDisabled);
    }

    @GetMapping("{id}")
    public ResponseEntity<ArticleDtoOut> getArticle(@PathVariable("id") @NotNull @Min(value = 1) Integer articleId) {
        var dto = articleService.getArticle(articleId);
        if (isNull(dto)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Transactional
    public ArticleDtoOut createOrUpdateArticle(@RequestBody ArticleDtoIn articleDtoIn) {
        return articleService.saveArticle(articleDtoIn);
    }

    @DeleteMapping("{id}")
    public void deleteArticle(@PathVariable("id") int id) {
        articleService.deleteArticle(id);
    }

    @PostMapping("{id}/price")
    public void createOrUpdatePrice(@PathVariable("id") @NotNull @Min(value = 1) Integer articleId,
                                    @RequestBody ArticlePriceDtoIn priceIn) {
        articleService.createOrUpdatePrice(articleId, priceIn);
    }

    @DeleteMapping("{id}/price")
    public void deletePrice(@PathVariable("id") @NotNull @Min(value = 1) Integer articleId,
                            @RequestBody ArticlePriceDtoIn priceDtoIn) {
        articleService.deletePrice(articleId, priceDtoIn);
    }

    @PostMapping("{id}/ingredients")
    public ArticleDtoOut createOrUpdateArticleIngredients(@PathVariable("id") @NotNull @Min(value = 1) Integer articleId,
                                                 @RequestBody ArticleIngredientsDtoIn dto) {
        return articleService.createOrUpdateArticleIngredients(articleId, dto);
    }
}
