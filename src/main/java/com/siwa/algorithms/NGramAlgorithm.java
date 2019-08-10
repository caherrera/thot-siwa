package com.siwa.algorithms;

import com.siwa.annotations.CompareAlgorithm;
import com.siwa.interfaces.SimilarityAlgorithm;

import info.debatty.java.stringsimilarity.NGram;

/**
 * This Class contain the N-Gram Algorithm to get the percent of similarity between two strings.
 */
@CompareAlgorithm(name = "ngram")
public class NGramAlgorithm extends SimilarityResponse implements SimilarityAlgorithm{
    /**
     * This method return the name of algorithm. Is used by default response to set the name of algorithm
     * @return {String} Return the name of algorithm
     */
    @Override
    public String getName() {
        return "ngram";
    }
    
    /**
     * This comparation algorithm compare the string similarity based on Normalized N-Gram BI-DIST and BI-SIM similarity algorithm.
     * @param {String} seed The string you will analize.
     * @param {String} comparation The string you will compare.
     * @return {float} Return the percent of similarity of the comparation
     */
    @Override
    public float calcule(String seed, String comparator) {
        // Calculate the index for strings with lengh < 2
        int index = (seed.length() == 1 || comparator.length() == 1) ? 1 : 2;
        
        // Apply the N-GRam Algorithm
        NGram ngram = new NGram(2);
        float similarity = (float) ngram.distance(seed, comparator);
        // The N-gram algorithm is a similarity. Not need calcule the complement 
        return (1 - similarity) * 100;
    }
}
