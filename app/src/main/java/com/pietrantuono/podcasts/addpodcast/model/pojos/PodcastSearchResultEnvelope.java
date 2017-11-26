
package com.pietrantuono.podcasts.addpodcast.model.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import diocan.pojos.PodcastImpl;

public class PodcastSearchResultEnvelope {

    @SerializedName("resultCount")
    @Expose
    private Integer resultCount;
    @SerializedName("results")
    @Expose
    private List<PodcastImpl> results = null;

    /**
     * 
     * @return
     *     The resultCount
     */
    public Integer getResultCount() {
        return resultCount;
    }

    /**
     * 
     * @param resultCount
     *     The resultCount
     */
    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    /**
     * 
     * @return
     *     The results
     */
    public List<PodcastImpl> getResults() {
        return results;
    }

    /**
     * 
     * @param results
     *     The results
     */
    public void setResults(List<PodcastImpl> results) {
        this.results = results;
    }

}
