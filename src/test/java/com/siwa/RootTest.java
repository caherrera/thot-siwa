package com.siwa;

import com.siwa.config.Router;
import spark.Spark;
import spark.servlet.SparkApplication;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpResponse;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import com.siwa.test.*;

public class RootTest {
    public static SparkClient client;
    /**
     * Define the Class next to test in the Despegar Library test 
     * @author acalvoa
     * @return 
     */
    @ClassRule 
    public static final SparkServerRule SPARK_SERVER = new SparkServerRule(http -> {
        http.port(4568);
        new Router(http);
        client = new SparkClient(http);
    });

    @Test
    public void rootTest() throws Exception {
        
        /* The second parameter indicates whether redirects must be followed or not */
        GetMethod get = client.get("/", false);
        HttpResponse httpResponse = client.execute(get);
        assertEquals(200, httpResponse.code());
        assertNotNull(client.getApplication());
    }
}
