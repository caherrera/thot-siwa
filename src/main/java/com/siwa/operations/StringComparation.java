package com.siwa.operations;
import java.util.Enumeration;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;

import com.siwa.annotations.StringOperation;
import com.siwa.errors.GenericResponseError;
import com.siwa.helpers.CompareAlgorithmMap;
import com.siwa.interfaces.Operation;
import com.siwa.interfaces.SimilarityAlgorithm;

import spark.Request;
import spark.Response;

/**
 * @api {get} /strings/:seed/operations?operation=compare&with={comparation}&criteria={criteria} Compare String.
 * @apiVersion 1.0.0
 * @apiDescription Compare an string with an other.
 * @apiName Compare String
 * @apiGroup Strings
 *
 * @apiParam {String} seed The string used to apply an operation.
 * @apiParam {String} with (Query String - Required) The string you want compare.
 * @apiParam {String} criteria (Query String - Optional) The criteria algorithm used to compare strings.
 * 
 * The criteria value can be:
 * - lineal (Lineal Algorithm)
 * - ncslineal (No Case Sensitive Lineal Algorithm)
 * - levenshtein (Levenshtein distance Algorithm)
 * - wlevenshtein (Weight Levenshtein distance Algorithm - Keyboard distance)
 * - damerau (Levenshtein-Damerau distance Algorithm)
 * - winkler (Jaro-Winkler divergence Algorithm)
 * - ngram (N-Gram distance Algorithm)
 * - cossine (Cossine Algorithm)
 * - jaccard (Jaccard Algorithm)
 * 
 * @apiExample {Http} Comparation usage:
 * GET /strings/example/operations?operation=compare&with=example
 * @apiExample {Http} Comparation with criteria usage:
 * GET /strings/example/operations?operation=compare&with=example&criteria=lineal
 *
 * @apiSuccess {Object[]} response Return an array with the operations results.
 * @apiSuccess {String} response.criteria Return the criteria algorithm used.
 * @apiSuccess {Float} response.similarity Return the similarity percent.
 */
@StringOperation(name = "compare")
public class StringComparation implements Operation{
    // Define the identifiers attributes.
    private static final String[] ARGS = { "with", "criteria" };
    private Response response;
    // The parameters used to call the action
    private String seed;
    private String comparator;
    private String criteria;
    
    /**
     * This is the constructor of the String comparation class. This class is a String operation.
     */
    public StringComparation() {
        super();
    }
    
    /**
     * This method resume the String comparation operation
     * @return {JSONObject} Generate the resume of operation with the arguments.
     */
    public JSONObject getOperationResume() {
        // Generate the resume of operation
        JSONObject resume = new JSONObject();
        resume.put("operation", "compare");
        // Add the arguments of operation
        JSONArray arguments = new JSONArray();
        for (String arg : StringComparation.ARGS) {
            arguments.put(arg);
        }
        resume.put("args", arguments);
        return resume;
    }
    
    /**
     * This method compare two strings in the object attributes seed and comparator.
     * If the comparator is not exists, return an error 422.
     * If the criteria is provided but not is a valid criteria, return an error 400.
     * @return {String} Return the string represent the response of string similarity.
     */
    private String compare() {
        // Verify if the comparator is null. If null throw 422 error
        if (this.comparator == null) {
            GenericResponseError error = new GenericResponseError("The parameter with is missing.", this.response, 422);
            return error.toString();
        }
        // If the parameters no response an error, we search the criteria into map of criterias to apply
        // and execute the the compare method. 
        Hashtable<String, SimilarityAlgorithm> algorithmMap = CompareAlgorithmMap.getInstance().getAlgorithmMap();
        if (algorithmMap.containsKey(this.criteria)) {
            SimilarityAlgorithm algorithm = algorithmMap.get(this.criteria);
            return algorithm.compare(this.seed, this.comparator);
        } else {
            // If the criteria not exists, then response with 400 error.
            GenericResponseError error = new GenericResponseError("The criteria " + this.criteria + " is not implemented.", 
                    this.response, 400);
            return error.toString();
        }
    }
    
    /**
     * This method execute a String Comparation Operation
     * to compare two strings and get her percent of similarity
     * @param {Request} request The Request Spark Server object of request.
     * @param {Response} response The Response Spark Server object of request.
     * @return {String} Return a JSON String with the result of compare operation.
     */
    public String exec(Request request, Response response) {
        // Set the parameters for request.
        this.response = response;
        this.seed = request.params("seed");
        this.comparator = request.queryParams("with");
        this.criteria = (request.queryParams("criteria") == null) ? "all" : request.queryParams("criteria");
        // Execute the comparation
        return this.compare();
    }
    
    /**
     * Get criterias for the String Comparation operation
     * @return {String} Return a response with the criteria operations available
     */
    public String getCriterias() {
        JSONArray response = new JSONArray();
        // Iterate the algorithms available to get all criterias.
        Hashtable<String, SimilarityAlgorithm> algorithmMap = CompareAlgorithmMap.getInstance().getAlgorithmMap();
        Enumeration<String> criterias = algorithmMap.keys();
        while (criterias.hasMoreElements()) {
            response.put(criterias.nextElement());
        }
        return response.toString();
    }
}
