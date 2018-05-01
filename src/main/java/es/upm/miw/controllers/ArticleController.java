package es.upm.miw.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.Provider;
import es.upm.miw.dtos.ArticleDto;
import es.upm.miw.repositories.core.ArticleRepository;
import es.upm.miw.repositories.core.ProviderRepository;
import es.upm.miw.services.DatabaseSeederService;

@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private DatabaseSeederService databaseSeederService;

    public Optional<ArticleDto> readArticle(String code) {
        Article article = this.articleRepository.findOne(code);
        if (article != null) {
            return Optional.of(new ArticleDto(article));
        } else {
            return Optional.empty();
        }
    }

    public Optional<ArticleDto> createArticle(ArticleDto articleDto) {
        Provider provider = null;
        String code = (articleDto.getCode() == null) ? this.databaseSeederService.createEan13() : articleDto.getCode();
        int stock = (articleDto.getStock() == null) ? 0 : articleDto.getStock();
        if (this.articleRepository.findOne(code) != null) {
            return Optional.empty();
        }
        if (articleDto.getProvider() != null) {
            provider = this.providerRepository.findOne(articleDto.getProvider());
        }
        Article articulo = new Article(code, articleDto.getDescription(), articleDto.getRetailPrice(), articleDto.getReference(), stock,
                provider);
        this.articleRepository.save(articulo);
        return Optional.of(articleDto);
    }

    public Optional<String> updateArticle(String code, ArticleDto articleDto) {
        Article article = this.articleRepository.findOne(code);
        if (article == null) {
            return Optional.of("code (" + code + ") not found");
        }
        article.setDescription(articleDto.getDescription());
        article.setReference(articleDto.getReference());
        article.setRetailPrice(articleDto.getRetailPrice());
        article.setStock(articleDto.getStock());
        Provider provider = null;
        if (articleDto.getProvider() != null) {
            provider = this.providerRepository.findOne(articleDto.getProvider());
        }
        article.setProvider(provider);
        this.articleRepository.save(article);
        return Optional.empty();
    }

    public Optional<String> updateArticleStock(String code, Integer stock) {
        Article article = this.articleRepository.findOne(code);
        if (article == null) {
            return Optional.of("code (" + code + ") not found");
        }
        article.setStock(stock);
        this.articleRepository.save(article);
        return Optional.empty();
    }

    public List<ArticleDto> findIncompletes() {
        return this.articleRepository
                .findByReferenceIsNullOrEmptyOrDescriptionIsNullOrEmptyOrRetailPriceIsNullOrZeroOrStockIsNullOrProviderIsNull();
    }

    public List<ArticleDto> findBy(String reference, String description, String provider) {
        List<Article> articleList;
        List<ArticleDto> articleListDto = new ArrayList<ArticleDto>();

        articleList = this.articleRepository.findByReferenceLikeIgnoreCaseAndDescriptionLikeIgnoreCaseAndProvider(reference, description,
                provider);

        for (Article article : articleList) {
            articleListDto.add(new ArticleDto(article));
        }

        return articleListDto;

    }

}
