package com.siwa;

import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpResponse;
import com.siwa.config.Router;
import com.siwa.errors.GenericResponseError;
import com.siwa.helpers.CompareAlgorithmMap;
import com.siwa.interfaces.SimilarityAlgorithm;
import com.siwa.test.SparkClient;
import com.siwa.test.SparkServerRule;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Hashtable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.ClassRule;
import org.junit.Test;

public class ComparatorControllerTest {
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
    public void testComparatorResume() throws Exception {
        /* The second parameter indicates whether redirects must be followed or not */
        GetMethod get = client.get("/api/strings/:seed/operations", false);
        HttpResponse httpResponse = client.execute(get);
        assertEquals(200, httpResponse.code());   
        // Verify the encoding
        assertEquals("application/json; charset=utf-8;", httpResponse.headers().get("Content-Type").get(0));
        // Verify the operation response
        JSONArray operations = new JSONArray();
        JSONObject comparation = new JSONObject();
        comparation.put("operation", "compare");
        comparation.put("args", new JSONArray().put("with").put("criteria"));
        operations.put(comparation);
        assertEquals(operations.toString(), new String(httpResponse.body()));
        assertNotNull(client.getApplication());
    }
    
    /**
     * This test ensure the String comparation response.
     * 
     * The JSON object response are
     * [
     *   {
     *     "algorithmn": "Algorithmn name",
     *     "similarity": Percent (float)
     *    }
     *  ]
     *  
     * @throws Exception
     */
    @Test
    public void testComparator() throws Exception {
        String seed ="Tes56t6%43$";
        String comparator = "Te21s56t678%$7";
        /* The second parameter indicates whether redirects must be followed or not */
        GetMethod get = client.get("/api/strings/" +  URLEncoder.encode(seed, "UTF-8") + "/operations?operation=compare&with=" + 
        URLEncoder.encode(comparator, "UTF-8"), false);
        HttpResponse httpResponse = client.execute(get);
        assertEquals(200, httpResponse.code());
        // Verify the encoding
        assertEquals("application/json; charset=utf-8;", httpResponse.headers().get("Content-Type").get(0));
        // Get compare algorithms table
        Hashtable<String, SimilarityAlgorithm> algorithms = CompareAlgorithmMap.getInstance().getAlgorithmMap();
        // Verify the response
        JSONArray response = new JSONArray();
        Enumeration<String> algorithmsNames = algorithms.keys();
        while (algorithmsNames.hasMoreElements()) {
            String algorithm = algorithmsNames.nextElement();
            if (!algorithm.equals("all")) response.put(algorithms.get(algorithm).getJSON(seed, comparator));
        }
        // Verify the response
        assertEquals(response.toString(), new String(httpResponse.body()));
        assertNotNull(client.getApplication());
    }
    /**
     * Compare when the strings are only one char and in upper and lower case
     * @throws Exception
     */
    @Test
    public void testOneCharComparator() throws Exception {
        String seed ="a";
        String comparator = "A";
        /* The second parameter indicates whether redirects must be followed or not */
        GetMethod get = client.get("/api/strings/" +  URLEncoder.encode(seed, "UTF-8") + "/operations?operation=compare&with=" + 
        URLEncoder.encode(comparator, "UTF-8"), false);
        HttpResponse httpResponse = client.execute(get);
        assertEquals(200, httpResponse.code());
        // Verify the encoding
        assertEquals("application/json; charset=utf-8;", httpResponse.headers().get("Content-Type").get(0));
        // Get compare algorithms table
        Hashtable<String, SimilarityAlgorithm> algorithms = CompareAlgorithmMap.getInstance().getAlgorithmMap();
        // Verify the response
        JSONArray response = new JSONArray();
        Enumeration<String> algorithmsNames = algorithms.keys();
        while (algorithmsNames.hasMoreElements()) {
            String algorithm = algorithmsNames.nextElement();
            if (!algorithm.equals("all")) response.put(algorithms.get(algorithm).getJSON(seed, comparator));
        }
        // Verify the response
        assertEquals(response.toString(), new String(httpResponse.body()));
        assertNotNull(client.getApplication());
    }
    
    /**
     * This test ensure the Levenshtein algorithmn works with alphanumeric strings
     * @throws Exception
     */
    @Test
    public void testComparatorAlphanumeric() throws Exception {
        String seed ="Tes56t6%43$";
        String comparator = "Te21s56t678%$7";
        /* The second parameter indicates whether redirects must be followed or not */
        GetMethod get = client.get("/api/strings/" +  URLEncoder.encode(seed, "UTF-8") + "/operations?operation=compare&with=" + 
        URLEncoder.encode(comparator, "UTF-8"), false);
        
        HttpResponse httpResponse = client.execute(get);
        assertEquals(200, httpResponse.code());
        // Verify the encoding
        assertEquals("application/json; charset=utf-8;", httpResponse.headers().get("Content-Type").get(0));
        // Get compare algorithms table
        Hashtable<String, SimilarityAlgorithm> algorithms = CompareAlgorithmMap.getInstance().getAlgorithmMap();
        // Verify the response
        JSONArray response = new JSONArray();
        Enumeration<String> algorithmsNames = algorithms.keys();
        while (algorithmsNames.hasMoreElements()) {
            String algorithm = algorithmsNames.nextElement();
            if (!algorithm.equals("all")) response.put(algorithms.get(algorithm).getJSON(seed, comparator));
        }
        // Verify the response
        assertEquals(response.toString(), new String(httpResponse.body()));
        assertNotNull(client.getApplication());
    }
    
    /**
     * This test ensure api response with error with ilegal operation.
     * @throws Exception
     */
    @Test
    public void badOperationTest() throws Exception {
        String seed = "testcomparator";
        GetMethod get = client.get("/api/strings/" + seed + "/operations?operation=invert", false);
        HttpResponse httpResponse = client.execute(get);
        assertEquals(400, httpResponse.code());
        // Verify the encoding
        assertEquals("application/json; charset=utf-8;", httpResponse.headers().get("Content-Type").get(0));
        // Verify the response
        assertEquals(GenericResponseError.toString("The operation invert its not defined."), new String(httpResponse.body()));
        assertNotNull(client.getApplication());
    }
    
    /**
     * This test ensure api response with error when operation compare has not with parameter
     * @throws Exception
     */
    @Test
    public void noWithOperationTest() throws Exception {
        String seed = "testcomparator";
        GetMethod get = client.get("/api/strings/" + seed + "/operations?operation=compare", false);
        HttpResponse httpResponse = client.execute(get);
        assertEquals(422, httpResponse.code());
        // Verify the encoding
        assertEquals("application/json; charset=utf-8;", httpResponse.headers().get("Content-Type").get(0));
        // Verify the response
        assertEquals(GenericResponseError.toString("The parameter with is missing."), new String(httpResponse.body()));
        assertNotNull(client.getApplication());
    }
    
    /**
     * This test ensure api response with error when operation compare has not with parameter
     * @throws Exception
     */
    @Test
    public void withCriteriaButNotWithTest() throws Exception {
        String seed = "testcomparator";
        GetMethod get = client.get("/api/strings/" + seed + "/operations?operation=compare", false);
        HttpResponse httpResponse = client.execute(get);
        assertEquals(422, httpResponse.code());
        // Verify the encoding
        assertEquals("application/json; charset=utf-8;", httpResponse.headers().get("Content-Type").get(0));
        // Verify the response
        assertEquals(GenericResponseError.toString("The parameter with is missing."), new String(httpResponse.body()));
        assertNotNull(client.getApplication());
    }
    
    /**
     * This test ensure api response with error when criteria is not valid
     * @throws Exception
     */
    @Test
    public void criteriaInvalidTest() throws Exception {
        String seed = "testcomparator";
        String comparator = "testother";
        GetMethod get = client.get("/api/strings/" + seed + "/operations?operation=compare&with=" + comparator + "&criteria=design", false);
        HttpResponse httpResponse = client.execute(get);
        assertEquals(400, httpResponse.code());
        // Verify the encoding
        assertEquals("application/json; charset=utf-8;", httpResponse.headers().get("Content-Type").get(0));
        // Verify the response
        assertEquals(GenericResponseError.toString("The criteria design is not implemented."), new String(httpResponse.body()));
        assertNotNull(client.getApplication());
    }
}
