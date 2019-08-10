package com.siwa.test;

import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Service;
/**
 * This an class to generate a Spark Server for testing purposes
 * @author ferwasy
 *
 */
public class SparkServerRule extends ExternalResource {

    private static final Logger LOG = LoggerFactory.getLogger(SparkServerRule.class);

    private ServiceInitializer serviceInitializer;
    private Service service;

    /**
     * Create Spark server rule with specified {@link ServiceInitializer}. You use the {@link ServiceInitializer}
     * to configure the Spark server port, IP address, security, routes, etc. Things like port and IP address must
     * be configured before routes.
     *
     * @see Service
     */
    public SparkServerRule(ServiceInitializer svcInit) {
        this.serviceInitializer = svcInit;
    }
    
    /**
     * Automatically ignite a new Spark server use the ignite method
     */
    @Override
    protected void before() throws Throwable {
        LOG.trace("Start spark server");
        service = Service.ignite();
        serviceInitializer.init(service);

        LOG.trace("Await initialization of Spark...");
        service.awaitInitialization();

        LOG.trace("Spark is ignited!");
    }
    
    /**
     * Automatically shutdown a new Spark server when testing operations it's finallized
     */
    @Override
    protected void after() {
        LOG.trace("Stopping Spark...");
        service.stop();

        LOG.trace("Spark stopped");
    }

}