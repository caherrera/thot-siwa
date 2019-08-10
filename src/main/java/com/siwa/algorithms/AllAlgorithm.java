package com.siwa.algorithms;

import java.util.Enumeration;
import java.util.Hashtable;

import org.json.JSONArray;

import com.siwa.annotations.CompareAlgorithm;
import com.siwa.helpers.CompareAlgorithmMap;
import com.siwa.interfaces.SimilarityAlgorithm;

/**
 * Iterate all Algorithm Map to get all compare algorithms results.
 */
@CompareAlgorithm(name = "all")
public class AllAlgorithm extends SimilarityResponse implements SimilarityAlgorithm{
    /**
     * This method return the name of algorithm. Is used by default response to set the name of algorithm
     * @return {String} Return the name of algorithm
     */
    @Override
    public String getName() {
        return "all";
    }
    
    /**
     * This method its not defined by the class, because is not necessary. The all Algorithms makes his response in 
     * base other algorithms responses
     * @return {float} This method not error nothing, because is not necesary
     */
    @Override
    public float calcule(String seed, String comparator) {
        // Return nothing 
        return 0;
    }
    
    /**
     * This method calcule the percent of similarity using the all Algorithms designed like Compare Algorithm
     * @param {String} seed The string you use like a base to compare.
     * @param {String} comparator The string used to compare with seed.
     * @return {String} Return an JSON String represent the comparation response.
     */
    public String compare(String seed, String comparator) {
        // Generate the JSON response
        JSONArray response = new JSONArray();
        Hashtable<String, SimilarityAlgorithm> algorithmsMap = CompareAlgorithmMap.getInstance().getAlgorithmMap();
        Enumeration<String> algorithmsNames = algorithmsMap.keys();
        while (algorithmsNames.hasMoreElements()) {
            String algorithm = algorithmsNames.nextElement();
            if (!algorithm.equals(this.getName())) response.put(algorithmsMap.get(algorithm).getJSON(seed, comparator));
        }
        return response.toString();
    }
}
