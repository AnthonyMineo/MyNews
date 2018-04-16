
package com.denma.mynews.Models.ArticleSearchAPI;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticleSearchResult {

    @SerializedName("docs")
    @Expose
    private List<ArticleSearchDoc> docs = null;
    @SerializedName("meta")
    @Expose
    private ArticleSearchMeta meta;

    public List<ArticleSearchDoc> getDocs() {
        return docs;
    }

    public void setDocs(List<ArticleSearchDoc> docs) {
        this.docs = docs;
    }

    public ArticleSearchMeta getMeta() {
        return meta;
    }

    public void setMeta(ArticleSearchMeta meta) {
        this.meta = meta;
    }

}