package com.siwa;

import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpResponse;
import com.siwa.config.Router;
import com.siwa.helpers.CompareAlgorithmMap;
import com.siwa.interfaces.SimilarityAlgorithm;
import com.siwa.test.SparkClient;
import com.siwa.test.SparkServerRule;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.net.URLEncoder;
import java.util.Hashtable;
import org.json.JSONObject;
import org.junit.ClassRule;
import org.junit.Test;

public class DamerauAlgorithTest {
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
    public void testDamerau() throws Exception {
        String seed = "TestDamerau";
        String comparator = "Test";
        /* The second parameter indicates whether redirects must be followed or not */
        GetMethod get = client.get("/api/strings/" + seed + "/operations?operation=compare&with=" + comparator + "&criteria=damerau", false);
        HttpResponse httpResponse = client.execute(get);
        assertEquals(200, httpResponse.code());
        // Verify the encoding
        assertEquals("application/json; charset=utf-8;", httpResponse.headers().get("Content-Type").get(0));
        // Get compare algorithms table
        Hashtable<String, SimilarityAlgorithm> algorithms = CompareAlgorithmMap.getInstance().getAlgorithmMap();
        // Make the response Object
        JSONObject response = new JSONObject();
        response.put("criteria", "damerau");
        response.put("similarity", algorithms.get("damerau").calcule(seed, comparator));
        // Verify the response
        assertEquals(response.toString(), new String(httpResponse.body()));
        assertNotNull(client.getApplication());
    }
    
    @Test
    public void testDamerauAlphanumeric() throws Exception {
        String seed = "Tes56t6%43$";
        String comparator = "Te21s56t678%$7";
        /* The second parameter indicates whether redirects must be followed or not */
        GetMethod get = client.get("/api/strings/" +  URLEncoder.encode(seed, "UTF-8") + "/operations?operation=compare&with=" + 
                URLEncoder.encode(comparator, "UTF-8") + "&criteria=damerau", false);
        HttpResponse httpResponse = client.execute(get);
        assertEquals(200, httpResponse.code());
        // Verify the encoding
        assertEquals("application/json; charset=utf-8;", httpResponse.headers().get("Content-Type").get(0));
        // Get compare algorithms table
        Hashtable<String, SimilarityAlgorithm> algorithms = CompareAlgorithmMap.getInstance().getAlgorithmMap();
        // Make the response Object
        JSONObject response = new JSONObject();
        response.put("criteria", "damerau");
        response.put("similarity", algorithms.get("damerau").calcule(seed, comparator));
        // Verify the response
        assertEquals(response.toString(), new String(httpResponse.body()));
        assertNotNull(client.getApplication());
    }
}
