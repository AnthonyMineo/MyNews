
package com.denma.mynews.Models.ArticleSearchAPI;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticleSearchResult {

    @SerializedName("docs")
    @Expose
    private List<ArticleSearchArticles> docs = null;
    @SerializedName("meta")
    @Expose
    private ArticleSearchMeta meta;

    public List<ArticleSearchArticles> getDocs() {
        return docs;
    }

    public void setDocs(List<ArticleSearchArticles> docs) {
        this.docs = docs;
    }

    public ArticleSearchMeta getMeta() {
        return meta;
    }

    public void setMeta(ArticleSearchMeta meta) {
        this.meta = meta;
    }

}