package com.denma.mynews.Models.TopStoriesAPI;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopStoriesResponse {

    @SerializedName("results")
    @Expose
    private List<TopStoriesArticles> results = null;

    public List<TopStoriesArticles> getResults() {
        return results;
    }

    public void setResults(List<TopStoriesArticles> results) {
        this.results = results;
    }

}