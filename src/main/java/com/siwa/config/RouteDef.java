package com.siwa.config;

import spark.Route;
/**
 * This is an Class to define the routes in the Router class.
 *
 */
public class RouteDef {
    // The attributes of an Route
    public String method;
    public String path;
    public Route expr;
    
    /**
     * Create a new route with the parameters
     * @param {String} method The http method you want to use in the route.
     * @param {String} path The URL path for the route.
     * @param {Route} expr The action that the route execute in requests.
     */
    public RouteDef(String method, String path, Route expr) {
        super();
        this.method = method;
        this.path = path;
        this.expr = expr;
    }
}
