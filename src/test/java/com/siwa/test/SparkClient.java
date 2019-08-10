package com.siwa.test;

import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpClient;
import com.despegar.http.client.HttpClientException;
import com.despegar.http.client.HttpMethod;
import com.despegar.http.client.HttpResponse;
import spark.Service;
/**
 * Its a client testing class to execute tests on the platform.
 * @author acalvoa
 *
 */
public class SparkClient {
    // Declare the elements needed
    private HttpClient client;
    private Service service;
    
    /**
     * The constructor of class create a new client for testing use
     * @param http 
     */
    public SparkClient(Service http) {
        this.service = http;
        this.client = new HttpClient(1);
    }
    
    public GetMethod get(String path, boolean followRedirect) {
        return new GetMethod("http://localhost:" + service.port() + path, followRedirect);
    }
    
    public HttpResponse execute(HttpMethod httpMethod) throws HttpClientException {
        return this.client.execute(httpMethod);
    }
    
    public Service getApplication() {
        return this.service;
    }
}
