package com.siwa.test;

import spark.Service;
/**
 * This an interface to generate a Spark Server for testing purposes
 * @author ferwasy
 *
 */
@FunctionalInterface
public interface ServiceInitializer {

    void init(Service service);

}