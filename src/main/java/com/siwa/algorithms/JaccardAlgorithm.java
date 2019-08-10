package com.siwa.algorithms;

import com.siwa.annotations.CompareAlgorithm;
import com.siwa.interfaces.SimilarityAlgorithm;

import info.debatty.java.stringsimilarity.Jaccard;

/**
 * This Class contain the Cosine N-gram Jaccard Algorithm to get the percent of similarity between two strings.
 */
@CompareAlgorithm(name = "jaccard")
public class JaccardAlgorithm extends SimilarityResponse implements SimilarityAlgorithm{
    /**
     * This method return the name of algorithm. Is used by default response to set the name of algorithm
     * @return {String} Return the name of algorithm
     */
    @Override
    public String getName() {
        return "jaccard";
    }
    
    /**
     * This comparation algorithm compare the string similarity based on Jaccard based N-Gram similarity algorithm.
     * We use a I-DIST and BI-SIM analisis.
     * @param {String} seed The string you will analize.
     * @param {String} comparation The string you will compare.
     * @return {float} Return the percent of similarity of the comparation
     */
    @Override
    public float calcule(String seed, String comparator) {
     // Calculate the index for strings with lengh < 2
        int index = (seed.length() == 1 || comparator.length() == 1) ? 1 : 2;
        // Apply the Jaccard algorithm
        Jaccard jaccard = new Jaccard(index);
        float similarity = (float) jaccard.similarity(seed, comparator);
        // The result of Jaccard N-gram algorithm is a similarity. Not need calcule the complement 
        return similarity * 100;
    }
}
