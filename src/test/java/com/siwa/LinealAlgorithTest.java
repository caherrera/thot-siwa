package com.siwa;

import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpResponse;
import com.despegar.sparkjava.test.SparkServer;
import com.siwa.config.Router;
import com.siwa.helpers.CompareAlgorithmMap;
import com.siwa.interfaces.SimilarityAlgorithm;
import com.siwa.test.SparkClient;
import com.siwa.test.SparkServerRule;
import spark.servlet.SparkApplication;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.net.URLEncoder;
import java.util.Hashtable;
import org.json.JSONObject;
import org.junit.ClassRule;
import org.junit.Test;

public class LinealAlgorithTest {
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
    
    /**
     * This test ensure the lineal algorithmn works with normal strings
     * @throws Exception
     */
    @Test
    public void testLineal() throws Exception {
        String seed = "linealtest";
        String comparator = "testlineal";
        /* The second parameter indicates whether redirects must be followed or not */
        GetMethod get = client.get("/api/strings/" + seed + "/operations?operation=compare&with=" + comparator + "&criteria=lineal", false);
        HttpResponse httpResponse = client.execute(get);
        assertEquals(200, httpResponse.code());
        // Verify the encoding
        assertEquals("application/json; charset=utf-8;", httpResponse.headers().get("Content-Type").get(0));
        // Get compare algorithms table
        Hashtable<String, SimilarityAlgorithm> algorithms = CompareAlgorithmMap.getInstance().getAlgorithmMap();
        // Make the response Object
        JSONObject response = new JSONObject();
        response.put("criteria", "lineal");
        response.put("similarity", algorithms.get("ncslineal").calcule(seed, comparator));
        // Verify the response
        assertEquals(response.toString(), new String(httpResponse.body()));
        assertNotNull(client.getApplication());
    }
    
    /**
     * This test ensure the lineal algorithmn works with alphanumeric strings
     * @throws Exception
     */
    @Test
    public void testLinealAlphanumeric() throws Exception {        
        String seed = "Tes56t6%43$";
        String comparator = "Te21s56t678%$7";
        /* The second parameter indicates whether redirects must be followed or not */
        GetMethod get = client.get("/api/strings/" + URLEncoder.encode(seed, "UTF-8") + "/operations?operation=compare&with=" + 
                URLEncoder.encode(comparator, "UTF-8") + "&criteria=lineal", false);
        HttpResponse httpResponse = client.execute(get);
        assertEquals(200, httpResponse.code());
        // Verify the encoding
        assertEquals("application/json; charset=utf-8;", httpResponse.headers().get("Content-Type").get(0));
        // Get compare algorithms table
        Hashtable<String, SimilarityAlgorithm> algorithms = CompareAlgorithmMap.getInstance().getAlgorithmMap();
        // Make the response Object
        JSONObject response = new JSONObject();
        response.put("criteria", "lineal");
        response.put("similarity", algorithms.get("ncslineal").calcule(seed, comparator));
        // Verify the response
        assertEquals(response.toString(), new String(httpResponse.body()));
        assertNotNull(client.getApplication());
    }
    
    /**
     * This test ensure the No Case Sensitive lineal algorithmn works with normal strings
     * @throws Exception
     */
    @Test
    public void testLinealNCS() throws Exception {
        String seed = "test";
        String comparator = "test";
        /* The second parameter indicates whether redirects must be followed or not */
        GetMethod get = client.get("/api/strings/" + seed + "/operations?operation=compare&with=" + comparator + "&criteria=ncslineal", false);
        HttpResponse httpResponse = client.execute(get);
        assertEquals(200, httpResponse.code());
        // Verify the encoding
        assertEquals("application/json; charset=utf-8;", httpResponse.headers().get("Content-Type").get(0));
        // Get compare algorithms table
        Hashtable<String, SimilarityAlgorithm> algorithms = CompareAlgorithmMap.getInstance().getAlgorithmMap();
        // Make the response Object
        JSONObject response = new JSONObject();
        response.put("criteria", "ncslineal");
        response.put("similarity", algorithms.get("ncslineal").calcule(seed, comparator));
        // Verify the response
        assertEquals(response.toString(), new String(httpResponse.body()));
        assertNotNull(client.getApplication());
    }
    
    /**
     * This test ensure the No case Sensitive lineal algorithmn works with alphanumeric strings
     * @throws Exception
     */
    @Test
    public void testLinealNCSAlphanumeric() throws Exception {        
        String seed = "Tes56t6%43$";
        String comparator = "Te21s56t678%$7";
        /* The second parameter indicates whether redirects must be followed or not */
        GetMethod get = client.get("/api/strings/" + URLEncoder.encode(seed, "UTF-8") + "/operations?operation=compare&with=" + 
                URLEncoder.encode(comparator, "UTF-8") + "&criteria=ncslineal", false);
        HttpResponse httpResponse = client.execute(get);
        assertEquals(200, httpResponse.code());
        // Verify the encoding
        assertEquals("application/json; charset=utf-8;", httpResponse.headers().get("Content-Type").get(0));
        // Get compare algorithms table
        Hashtable<String, SimilarityAlgorithm> algorithms = CompareAlgorithmMap.getInstance().getAlgorithmMap();
        // Make the response Object
        JSONObject response = new JSONObject();
        response.put("criteria", "ncslineal");
        response.put("similarity", algorithms.get("ncslineal").calcule(seed, comparator));
        // Verify the response
        assertEquals(response.toString(), new String(httpResponse.body()));
        assertNotNull(client.getApplication());
    }
}
