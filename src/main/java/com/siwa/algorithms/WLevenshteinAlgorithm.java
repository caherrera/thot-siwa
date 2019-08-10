package com.siwa.algorithms;

import com.siwa.annotations.CompareAlgorithm;
import com.siwa.interfaces.SimilarityAlgorithm;

import info.debatty.java.stringsimilarity.CharacterSubstitutionInterface;
import info.debatty.java.stringsimilarity.WeightedLevenshtein;

/**
 * This Class contain the Weight Levenshtein Algorithm to get the percent of similarity between two strings.
 */
@CompareAlgorithm(name = "wlevenshtein")
public class WLevenshteinAlgorithm extends SimilarityResponse implements SimilarityAlgorithm{
    /**
     * This method return the name of algorithm. Is used by default response to set the name of algorithm
     * @return {String} Return the name of algorithm
     */
    @Override
    public String getName() {
        return "wlevenshtein";
    }
    
    /**
     * This comparation algorithm compare the string similarity based on Weight Levenshtein distance algorithm.
     * based on qwerty keyboard with some comun erros. This algorithm can be perform if add more cases of errors
     * @param {String} seed The string you will analize.
     * @param {String} comparation The string you will compare.
     * @return {float} Return the percent of similarity of the comparation
     */
    @Override
    public float calcule(String seed, String comparator) {
        // First We will determine the most large and set diferences
        float length = (seed.length() < comparator.length()) ? comparator.length() : seed.length();
        // Apply the Weight Leveshtein distance algorithm
        WeightedLevenshtein wLevenshteinDistance = new WeightedLevenshtein(new CharacterSubstitutionInterface() {
            public double cost(char c1, char c2) {
                // The cost for substituting 't' and 'r' is considered
                // smaller as these 2 are located next to each other
                // on a keyboard
                if (c1 == 't' && c2 == 'r') {
                    return 0.5;
                } else if (c1 == 'a' && c2 == 's') {
                    return 0.5;
                } else if (c1 == 'b' && c2 == 'v') {
                    return 0.5;
                } else if (c1 == 'z' && c2 == 'x') {
                    return 0.5;
                } else if (c1 == 's' && c2 == 'd') {
                    return 0.5;
                } else if (c1 == 'n' && c2 == 'm') {
                    return 0.5;
                } else if (c1 == 'i' && c2 == 'o') {
                    return 0.5;
                } else if (c1 == 'k' && c2 == 'l') {
                    return 0.5;
                } else if (c1 == 'o' && c2 == 'p') {
                    return 0.5;
                }
                // For the all cases the cost of sustitution is
                // is 1.0
                return 1.0;
            }
        });
        int distance = (int) wLevenshteinDistance.distance(seed, comparator);
        // We must return 1 - diference to return the similarity
        return (1 - (distance / length)) * 100;
    }
}
