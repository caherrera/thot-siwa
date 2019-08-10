package com.siwa.controllers;

import java.util.Enumeration;
import java.util.Hashtable;

import org.json.JSONArray;

import com.siwa.errors.GenericResponseError;
import com.siwa.helpers.StringOperationsMap;
import com.siwa.interfaces.Operation;
import com.siwa.operations.StringComparation;

import spark.Request;
import spark.Response;
/**
 * In this controller can be do differents operations for the specified string.
 * @author acalvoa
 *
 */
public class StringsController {
    /**
     * @api {get} /strings/:seed/operations String Operations.
     * @apiVersion 1.0.0
     * @apiDescription Apply an operation to a specified string.
     * @apiName Operations
     * @apiGroup Strings
     *
     * @apiSuccess {Object[]} response Return an array with the operations posible.
     * @apiSuccess {String} response.operation Provide the name of the operation
     * @apiSuccess {String} response.args Provide the arguments for the method
     */
    public static String operations(Request request, Response response) {
        // Set Response Encode
        response.header("content-type", "application/json; charset=utf-8;");
        
        // Get operation param to determine the action
        String operation = request.queryParams("operation");
        
        // Analize if neeed the operations resume
        if (operation == null || operation.isEmpty()) {
            return StringsController.getOperations();
        }
        
        // Get String Operations table
        Hashtable<String, Operation> operations = StringOperationsMap.getInstance().getAlgorithmMap();
        
        // Determine the operation find in the operation table
        if (operations.containsKey(operation)) {
            return operations.get(operation).exec(request, response);
        } else {
            GenericResponseError error = new GenericResponseError("The operation " + operation + " its not defined.", response, 400);
            return error.toString();
        }
    }
    
    /**
     * Resume the operations available in the String operation controller
     * @return {String} Return the JSON response that resume the operation posibles
     */
    private static String getOperations() {
        JSONArray response = new JSONArray();
        // Get String Operations table
        Hashtable<String, Operation> operations = StringOperationsMap.getInstance().getAlgorithmMap();
        // Iterate all operations and set descriptor
        Enumeration<String> algorithmsNames = operations.keys();
        while (algorithmsNames.hasMoreElements()) {
            String algorithm = algorithmsNames.nextElement();
            response.put(operations.get(algorithm).getOperationResume());
        }
        // Return the operations array response
        return response.toString();
    }
    
    /**
     * @api {get} /strings/operations/compare/criterias Get compare criterias.
     * @apiVersion 1.0.0
     * @apiDescription get the criterias available for the String comparation operation.
     * @apiName Operations
     * @apiGroup Strings
     *
     * @apiSuccess {String[]} response Return an array with the operations posible.
     */
    
    /**
     * Get criteria compare Operation options
     * @return {String} Return the JSON response that resume the criterias posible.
     */
    public static String getCompareCriteria(Request request, Response response) {
        // Call the method in object.
        StringComparation comparator = new StringComparation();
        return comparator.getCriterias();
    }
} 
