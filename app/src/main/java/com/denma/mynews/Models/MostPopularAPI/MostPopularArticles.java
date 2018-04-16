package com.denma.mynews.Models.MostPopularAPI;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MostPopularArticles {

    @SerializedName("url")
    private String url;
    @SerializedName("adx_keywords")
    private String adxKeywords;
    @SerializedName("column")
    private String column;
    @SerializedName("section")
    private String section;
    @SerializedName("byline")
    private String byline;
    @SerializedName("type")
    private String type;
    @SerializedName("title")
    private String title;
    @SerializedName("abstract")
    private String _abstract;
    @SerializedName("published_date")
    private String publishedDate;
    @SerializedName("source")
    private String source;
    @SerializedName("id")
    private double id;
    @SerializedName("asset_id")
    private double assetId;
    @SerializedName("views")
    private Integer views;
    @SerializedName("media")
    private List<MostPopularMedias> media = null;

    //Manual deserialisation

    private String desFacet;
    private String orgFacet;
    private String perFacet;
    private String geoFacet;

    List<String> ListDesFacet;
    List<String> ListOrgFacet;
    List<String> ListPerFacet;
    List<String> ListGeoFacet;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdxKeywords() {
        return adxKeywords;
    }

    public void setAdxKeywords(String adxKeywords) {
        this.adxKeywords = adxKeywords;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstract() {
        return _abstract;
    }

    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public double getAssetId() {
        return assetId;
    }

    public void setAssetId(double assetId) {
        this.assetId = assetId;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getDesFacet() {
        return desFacet;
    }

    public void setDesFacet(String desFacet) {
        this.desFacet = desFacet;
    }

    public String getOrgFacet() {
        return orgFacet;
    }

    public void setOrgFacet(String orgFacet) {
        this.orgFacet = orgFacet;
    }

    public String getPerFacet() { return perFacet; }

    public void setPerFacet(String perFacet) {
        this.perFacet = perFacet;
    }

    public String getGeoFacet() {
        return geoFacet;
    }

    public void setGeoFacet(String geoFacet) {
        this.geoFacet = geoFacet;
    }

    public List<String> getListDesFacet() { return ListDesFacet; }

    public void setListDesFacet(List<String> listDesFacet){ ListDesFacet = listDesFacet; }

    public List<String> getListOrgFacet() { return ListOrgFacet; }

    public void setListOrgFacet(List<String> listOrgFacet) { ListOrgFacet = listOrgFacet; }

    public List<String> getListPerFacet() { return ListPerFacet; }

    public void setListPerFacet(List<String> listPerFacet) { ListPerFacet = listPerFacet; }

    public List<String> getListGeoFacet() { return ListGeoFacet; }

    public void setListGeoFacet(List<String> listGeoFacet) { ListGeoFacet = listGeoFacet; }

    public List<MostPopularMedias> getMedia() {
        return media;
    }

    public void setMedia(List<MostPopularMedias> media) {
        this.media = media;
    }

    public static class myDeserializer implements JsonDeserializer<MostPopularArticles> {

        @Override
        public MostPopularArticles deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            MostPopularArticles mostPopularArticles = new Gson().fromJson(json, MostPopularArticles.class);
            JsonObject jsonObject = json.getAsJsonObject();

            if (jsonObject.has("des_facet")) {
                JsonElement elem = jsonObject.get("des_facet");
                if (elem != null && !elem.isJsonNull()) {
                    try {
                        mostPopularArticles.setDesFacet(elem.getAsString());
                    }catch (IllegalStateException e){
                        List<String> values = new Gson().fromJson(elem, new TypeToken<List<String>>() {}.getType());
                        mostPopularArticles.setListDesFacet(values);
                    }
                }
            }
            if (jsonObject.has("org_facet")) {
                JsonElement elem = jsonObject.get("org_facet");
                if (elem != null && !elem.isJsonNull()) {
                    try {
                        mostPopularArticles.setOrgFacet(elem.getAsString());
                    }catch (IllegalStateException e){
                        List<String> values = new Gson().fromJson(elem, new TypeToken<List<String>>() {}.getType());
                        mostPopularArticles.setListOrgFacet(values);
                    }
                }
            }
            if (jsonObject.has("per_facet")) {
                JsonElement elem = jsonObject.get("per_facet");
                if (elem != null && !elem.isJsonNull()) {
                    try {
                        mostPopularArticles.setPerFacet(elem.getAsString());
                    }catch (IllegalStateException e){
                        List<String> values = new Gson().fromJson(elem, new TypeToken<List<String>>() {}.getType());
                        mostPopularArticles.setListPerFacet(values);
                    }
                }
            }
            if (jsonObject.has("geo_facet")) {
                JsonElement elem = jsonObject.get("geo_facet");
                if (elem != null && !elem.isJsonNull()) {
                    try {
                        mostPopularArticles.setGeoFacet(elem.getAsString());
                    }catch (IllegalStateException e){
                        List<String> values = new Gson().fromJson(elem, new TypeToken<List<String>>() {}.getType());
                        mostPopularArticles.setListGeoFacet(values);
                    }
                }
            }
            return mostPopularArticles ;
        }
    }
}