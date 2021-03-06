package es.upm.miw.dtos;

import javax.validation.constraints.NotNull;

import es.upm.miw.documents.core.Article;

public class ArticleMinimumDto {

    private String code;

    @NotNull
    private String description;

    public ArticleMinimumDto() {
    }

    public ArticleMinimumDto(String code, String description) {
        super();
        this.code = code;
        this.description = description;
    }

    public ArticleMinimumDto(Article article) {
        this.code = article.getCode();
        this.description = article.getDescription();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ArticleMinimumDto [code=" + code + ", description=" + description + "]";
    }

}
