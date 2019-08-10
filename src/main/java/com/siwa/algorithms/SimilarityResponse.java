package com.siwa.algorithms;

import org.json.JSONObject;

import com.siwa.interfaces.SimilarityAlgorithm;

public abstract class SimilarityResponse implements SimilarityAlgorithm{
    /**
     * this method of abstract class create a default responses for all classes
     * that extends and implement the SimilarityResponse and SimilarityAlgorithm
     * @param {String} seed The string you use like a base to compare.
     * @param {String} comparator The string used to compare with seed.
     * @return {JSONObject} Return an JSON Object represent the comparation.
     */
    public JSONObject getJSON(String seed, String comparator) {
        // Generate the JSON response
        JSONObject response = new JSONObject();
        response.put("criteria", this.getName());
        response.put("similarity", this.calcule(seed, comparator));
        return response;
    }
    
    /**
     * This method execute the comparation and return them in JSON String format.
     * @param {String} seed The string you use like a base to compare.
     * @param {String} comparator The string used to compare with seed.
     * @return {String} Return an JSON String represent the comparation response.
     */
    public String compare(String seed, String comparator) {
        // Generate the JSON response
        return this.getJSON(seed, comparator).toString();
    }
}
