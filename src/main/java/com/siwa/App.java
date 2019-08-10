package com.siwa;

import org.apache.log4j.BasicConfigurator;

import com.siwa.config.Router;
import com.siwa.helpers.CompareAlgorithmMap;
import com.siwa.helpers.StringOperationsMap;

/**
 * Initialize the Siwa app, map the algorithms and set the routes.
 */
public class App {
    public static void main(String[] args) {
        // Configure the log
        BasicConfigurator.configure();
        // Set the map of operations and algorithms
        new CompareAlgorithmMap();
        new StringOperationsMap();
        // Initialize the Router.
        new Router();
    }
}
