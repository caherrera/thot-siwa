package com.siwa.algorithms;

import com.siwa.annotations.CompareAlgorithm;
import com.siwa.interfaces.SimilarityAlgorithm;

/**
 * This Class contain the Lineal Algorithm to get the percent of similarity between two strings.
 */
@CompareAlgorithm(name = "lineal")
public class LinealAlgorithm extends SimilarityResponse implements SimilarityAlgorithm{
    /**
     * This method return the name of algorithm. Is used by default response to set the name of algorithm
     * @return {String} Return the name of algorithm
     */
    @Override
    public String getName() {
        return "lineal";
    }
    
    /**
     * This comparation algorithm compare the string in lineal direction based on the secuence of their chars
     * @param {String} seed The string you will analize.
     * @param {String} comparation The string you will compare.
     * @return {float} Return the percent of similarity of the comparation
     */
    @Override
    public float calcule(String seed, String comparator) {
        // First We will determine the most large and set diferences
        float length = (seed.length() < comparator.length()) ? comparator.length() : seed.length();
        int different = Math.abs(seed.length() - comparator.length());
        // Iterate the string finding differences in the chars
        for(int i=0; i<seed.length(); i++) {
            if(comparator.length()-1 < i) break;
            if(seed.charAt(i) != comparator.charAt(i)) different++;
        }
        // We must return 1 - diference to return the similarity
        return (1 - (different / length)) * 100;
    }
}
