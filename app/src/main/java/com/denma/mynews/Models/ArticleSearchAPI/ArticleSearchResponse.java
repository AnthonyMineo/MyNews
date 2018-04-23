package com.denma.mynews.Models.ArticleSearchAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticleSearchResponse {

    @SerializedName("response")
    @Expose
    private ArticleSearchResult response;

    public ArticleSearchResult getResponse() {
        return response;
    }

    public void setResponse(ArticleSearchResult response) {
        this.response = response;
    }

}