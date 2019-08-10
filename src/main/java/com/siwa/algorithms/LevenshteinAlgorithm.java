package com.siwa.algorithms;

import com.siwa.annotations.CompareAlgorithm;
import com.siwa.interfaces.SimilarityAlgorithm;

import info.debatty.java.stringsimilarity.Levenshtein;

/**
 * This Class contain the Levenshtein Algorithm to get the percent of similarity between two strings.
 */
@CompareAlgorithm(name = "levenshtein")
public class LevenshteinAlgorithm extends SimilarityResponse implements SimilarityAlgorithm{
    /**
     * This method return the name of algorithm. Is used by default response to set the name of algorithm
     * @return {String} Return the name of algorithm
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "levenshtein";
    }
    
    /**
     * This comparation algorithm compare the string similarity based on Levenshtein distance algorithm.
     * @param {String} seed The string you will analize.
     * @param {String} comparation The string you will compare.
     * @return {float} Return the percent of similarity of the comparation
     */
    @Override
    public float calcule(String seed, String comparator) {
        // First We will determine the most large and set diferences
        float length = (seed.length() < comparator.length()) ? comparator.length() : seed.length();
        // Apply the leveshtein distance algorithm
        Levenshtein levenshteinDistance = new Levenshtein();
        int distance = (int) levenshteinDistance.distance(seed, comparator);
        // We must return 1 - diference to return the similarity
        return (1 - (distance / length)) * 100;
    }
}
