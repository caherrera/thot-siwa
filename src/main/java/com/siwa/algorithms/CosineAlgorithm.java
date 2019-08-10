package com.siwa.algorithms;

import java.util.Map;

import com.siwa.annotations.CompareAlgorithm;
import com.siwa.interfaces.SimilarityAlgorithm;

import info.debatty.java.stringsimilarity.Cosine;

/**
 * This Class contain the Cosine Algorithm to get the percent of similarity between two strings.
 */
@CompareAlgorithm(name = "cosine")
public class CosineAlgorithm extends SimilarityResponse implements SimilarityAlgorithm {
    /**
     * This method return the name of algorithm. Is used by default response to set the name of algorithm
     * @return {String} Return the name of algorithm
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "cosine";
    }
    
    /**
     * This comparation algorithm compare the string similarity based on Cosine based N-Gram similarity algorithm.
     * We use a BI-DIST and BI-SIM analysis.
     * @param {String} seed The string you will analyze.
     * @param {String} comparation The string you will compare.
     * @return {float} Return the percent of similarity of the comparation
     */
    @Override
    public float calcule(String seed, String comparator) {
        // Calculate the index for strings with lengh < 2
        int index = (seed.length() == 1 || comparator.length() == 1) ? 1 : 2;
        
        // Apply the Cosine algorithm
        Cosine cosine = new Cosine(index);
        
        // Calcule the strings cosine profiles.
        Map<String, Integer> profileSeed = cosine.getProfile(seed);
        Map<String, Integer> profileComparation = cosine.getProfile(comparator);
        float similarity = (float) cosine.similarity(profileSeed, profileComparation);
        
        // The result of Cosine N-gram algorithm is a similarity. Not need calcule the complement 
        return similarity * 100;
    }
    
}
