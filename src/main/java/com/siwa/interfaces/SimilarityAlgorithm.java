package com.siwa.interfaces;

import org.json.JSONObject;

/**
 * This interface is used to set with a standard set of methods to a Compare Operation class.
 */
public interface SimilarityAlgorithm {
    /**
     * This method provide the percent of similarity between two strings.
     * @param {String} seed The string you will analyze.
     * @param {String} comparator The string you will compare.
     * @return {float} Return the percent of similarity of the comparation.
     */
    public float calcule(String seed, String comparator);
    
    /**
     * this method return the JSON response for operation
     * @param {String} seed The string you use like a base to use in algorithm.
     * @param {String} comparator The string use like second string in the algorithm.
     * @return {JSONObject} Return an JSON Object represent the algorithm.
     */
    public JSONObject getJSON(String seed, String comparator);
    
    /**
     * this method return the JSON String response for algorithm
     * @param {String} seed The string you use like a base to use in algorithm.
     * @param {String} comparator The string use like second string in the algorithm.
     * @return {String} Return an JSON String represent the algorithm.
     */
    public String compare(String seed, String comparator);
    
    /**
     * This method return the name of algorithm.
     * @return {String} Return the name of algorithm
     */
    public String getName();
}
