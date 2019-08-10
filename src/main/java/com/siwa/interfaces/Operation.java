package com.siwa.interfaces;

import org.json.JSONObject;

import spark.Request;
import spark.Response;

/**
 * This interface is used to set with a standard set of methods to a String Operation class.
 */
public interface Operation {
    /**
     * This method execute a the Operation
     * @param {Request} request The Request Spark Server object of request.
     * @param {Response} response The Response Spark Server object of request.
     * @return {String} Return a JSON String with the result of operation.
     */
    public String exec(Request request, Response response);
    
    /**
     * This method resume the operation and this components.
     * @return {JSONObject} Generate the resume of operation with the arguments.
     */
    public JSONObject getOperationResume();
}
