package com.siwa.algorithms;

import com.siwa.annotations.CompareAlgorithm;
import com.siwa.interfaces.SimilarityAlgorithm;

import info.debatty.java.stringsimilarity.JaroWinkler;

/**
 * This Class contain the Jaro-Winkler Algorithm to get the percent of similarity between two strings.
 */
@CompareAlgorithm(name = "winkler")
public class WinklerAlgorithm extends SimilarityResponse implements SimilarityAlgorithm{
    /**
     * This method return the name of algorithm. Is used by default response to set the name of algorithm
     * @return {String} Return the name of algorithm
     */
    @Override
    public String getName() {
        return "winkler";
    }
    
    /**
     * This comparation algorithm compare the string similarity based on Jaro-Winkler divergence algorithm.
     * @param {String} seed The string you will analize.
     * @param {String} comparation The string you will compare.
     * @return {float} Return the percent of similarity of the comparation
     */
    @Override
    public float calcule(String seed, String comparator) {
        // Apply the Jaro-winkler algorithm
        JaroWinkler jaroWinkler = new JaroWinkler();
        float similarity = (float) jaroWinkler.similarity(seed, comparator);
        // Because Jaro-winkler it's a similarity algorithm we must only return the percent.
        return similarity * 100;
    }
}
