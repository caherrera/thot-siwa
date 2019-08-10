package com.siwa.config;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.delete;
import static spark.Spark.patch;
import static spark.Spark.put;
import static spark.Spark.redirect;
import spark.Service;
import spark.Spark;

import com.siwa.controllers.StringsController;


/**
 * The class router set the default routes in the Siwa App
 */
public class Router {
    /**
     * This Array is the routes in the App. Each route are and routerDef object.
     */
    private RouteDef[] routes = {
        new RouteDef("GET", "/api/strings/:seed/operations", (req, res) -> StringsController.operations(req, res)),
        new RouteDef("GET", "/api/strings/operations/compare/criterias", (req, res) -> StringsController.getCompareCriteria(req, res))
    };
    
    /**
     * Its the constructor of router class. Initialize the router for RESTFul API.
     */
    public Router() {
        // Set the static files
        Spark.staticFiles.location("/public");
        // Redirect Api to api/ to get the apidoc
        redirect.get("/api", "/api/");
        this.setRoutes();
    }
    /**
     * Its the constructor of router class. Initialize the router for RESTFul API.
     * @param {Service} http Provide the http service of spark ignite instance
     */
    public Router(Service http) {
        // Set the static files
        http.staticFiles.location("/public");
        // Redirect Api to api/ to get the apidoc
        http.redirect.get("/api", "/api/");
        // Set the routes
        this.setRoutes(http);
    }
    
    /**
     * This method iterate the routes and set to deploy into spark server
     */
    private void setRoutes() {
        //Iterate the array with the routes
        for (int i=0; i<this.routes.length; i++) {
            switch (this.routes[i].method.toUpperCase()) {
                case "GET":
                    get(this.routes[i].path, this.routes[i].expr);
                    break;
                case "POST":
                    post(this.routes[i].path, this.routes[i].expr);
                    break;
                case "DELETE":
                    delete(this.routes[i].path, this.routes[i].expr);
                    break;
                case "PATCH":
                    patch(this.routes[i].path, this.routes[i].expr);
                    break;
                case "PUT":
                    put(this.routes[i].path, this.routes[i].expr);
                    break;
                default:
                    throw new Error(this.routes[i].method.toUpperCase() + " its no a valid method");
            }
        }
    }
    
    /**
     * This method iterate the routes and set to deploy into spark server is Service instance is available
     * @param {Service} http Provide the http service of spark ignite instance
     */
    public void setRoutes(Service http) {
        //Iterate the array with the routes
        for (int i=0; i<routes.length; i++) {
            switch (routes[i].method.toUpperCase()) {
                case "GET":
                    http.get(routes[i].path, routes[i].expr);
                    break;
                case "POST":
                    http.post(routes[i].path, routes[i].expr);
                    break;
                case "DELETE":
                    http.delete(routes[i].path, routes[i].expr);
                    break;
                case "PATCH":
                    http.patch(routes[i].path, routes[i].expr);
                    break;
                case "PUT":
                    http.put(routes[i].path, routes[i].expr);
                    break;
                default:
                    throw new Error(routes[i].method.toUpperCase() + " its no a valid method");
            }
        }
    }
}
