package com.siwa.errors;

import org.json.JSONObject;

import spark.Response;

public class GenericResponseError {
    // Message provide by the user for the error message
    private String message;
    
    /**
     * Its a generic error response to use in HTTP handler request.
     * @param {String} message Represent the message in the error
     */
    public GenericResponseError(String message) {
        this.message = message;
    }
    
    /**
     * Its a generic error response to use in HTTP handler request.
     * @param {String} message Represent the message in the error
     * @param {Response} response Represent the response object of controller
     * @param {int} code The error code of http request
     */
    public GenericResponseError(String message, Response response, int code) {
        this.message = message;
        response.status(code);
    }
    
    /**
     * Returns an JSONOject with error format to response to HTTP API request from static context.
     * @param {String} message Provide the message that display the error.
     * @return {String} Return the JSONObject error in String format.
     */
    public static String toString(String message) {
        JSONObject response = new JSONObject();
        response.put("status", "error");
        response.put("error", message);
        return response.toString();
    }
    
    /**
     * Returns an JSONOject with error format to response to HTTP API request.
     * @return {String} Return the JSONObject error in String format.
     */
    public String toString() {
        JSONObject response = new JSONObject();
        response.put("status", "error");
        response.put("error", message);
        return response.toString();
    }
}
