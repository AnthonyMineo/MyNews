package com.denma.mynews.Models.MostPopularAPI;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MostPopularResponse {

    @SerializedName("results")
    @Expose
    private List<MostPopularArticles> results = null;

    public List<MostPopularArticles> getResults() {
        return results;
    }

    public void setResults(List<MostPopularArticles> results) {
        this.results = results;
    }

}