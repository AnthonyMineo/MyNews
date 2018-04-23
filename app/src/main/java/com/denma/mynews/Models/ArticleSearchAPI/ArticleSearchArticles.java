package com.denma.mynews.Models.ArticleSearchAPI;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticleSearchArticles {

    @SerializedName("web_url")
    @Expose
    private String webUrl;
    @SerializedName("snippet")
    @Expose
    private String snippet;
    @SerializedName("multimedia")
    @Expose
    private List<ArticleSearchMedias> multimedia = null;
    @SerializedName("headline")
    @Expose
    private ArticleSearchHeadline headline;
    @SerializedName("keywords")
    @Expose
    private List<ArticleSearchKeyword> keywords = null;
    @SerializedName("pub_date")
    @Expose
    private String pubDate;
    @SerializedName("new_desk")
    @Expose
    private String newDesk;
    @SerializedName("section_name")
    @Expose
    private String sectionName;

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public List<ArticleSearchMedias> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<ArticleSearchMedias> multimedia) {
        this.multimedia = multimedia;
    }

    public ArticleSearchHeadline getHeadline() {
        return headline;
    }

    public void setHeadline(ArticleSearchHeadline headline) {
        this.headline = headline;
    }

    public List<ArticleSearchKeyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<ArticleSearchKeyword> keywords) {
        this.keywords = keywords;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getNewDesk() {
        return newDesk;
    }

    public void setNewDesk(String newDesk) {
        this.newDesk = newDesk;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

}