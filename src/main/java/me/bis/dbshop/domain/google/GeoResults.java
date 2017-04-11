package me.bis.dbshop.domain.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoResults {

    private JsonResults results;

    public JsonResults getResults() {
        return results;
    }
}
