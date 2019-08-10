package com.siwa.algorithms;

import com.siwa.annotations.CompareAlgorithm;
import com.siwa.interfaces.SimilarityAlgorithm;

/**
 * This Class contain the No Case Sensitive Lineal Algorithm to get the percent of similarity between two strings.
 */
@CompareAlgorithm(name = "ncslineal")
public class NCSLinealAlgorithm extends SimilarityResponse implements SimilarityAlgorithm{
    /**
     * This method return the name of algorithm. Is used by default response to set the name of algorithm
     * @return {String} Return the name of algorithm
     */
    @Override
    public String getName() {
        return "ncslineal";
    }
    
    /**
     * This comparation algorithm compare the string in lineal direction based on the secuence of their chars
     * but ignore the Case sensitive. The chars a ad A it's the same.
     * @param {String} seed The string you will analize.
     * @param {String} comparation The string you will compare.
     * @return {float} Return the percent of similarity of the comparation
     */
    @Override
    public float calcule(String seed, String comparator) {
        // First we transform two word in Lowercase
        seed = seed.toLowerCase();
        comparator = comparator.toLowerCase();
        // We will determine the most large and set diferences
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
