
package com.denma.mynews.Models.ArticleSearchAPI;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticleSearchResult {

    @SerializedName("docs")
    @Expose
    private List<ArticleSearchArticles> articleSearchArticles = null;

    public List<ArticleSearchArticles> getArticleSearchArticles() {
        return articleSearchArticles;
    }

    public void setArticleSearchArticles(List<ArticleSearchArticles> docs) {
        this.articleSearchArticles = docs;
    }

}