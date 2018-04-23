package com.denma.mynews.Models.MostPopularAPI;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MostPopularMedias {

    @SerializedName("media-metadata")
    @Expose
    private List<MostPopularMediasMetaData> mediaMetadata = null;

    public List<MostPopularMediasMetaData> getMediaMetadata() {
        return mediaMetadata;
    }

    public void setMediaMetadata(List<MostPopularMediasMetaData> mediaMetadata) {
        this.mediaMetadata = mediaMetadata;
    }

}