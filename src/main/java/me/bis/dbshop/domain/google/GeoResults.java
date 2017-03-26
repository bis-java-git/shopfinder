package me.bis.dbshop.domain.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoResults {

    private Results results;

    public Results getResults() {
        return results;
    }
}
