package eu.specsolutions.bddcourse.geekpizza.api_tests.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import io.cucumber.spring.ScenarioScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
@ScenarioScope
public class WebApiContext {

    @Autowired
    protected TestRestTemplate restTemplate;

    public WebApiContext(){
        // initialize cookie manager to be able to manage user login sessions
        CookieHandler.setDefault(new CookieManager());
    }

    public <T> T executeGet(String url, Class<T> responseType) {

        // execute request
        ResponseEntity<T> response = restTemplate.getForEntity(url, responseType);

        // sanity check (the response is a meaningful value, not e.g. 500)
        assertTrue(response.getStatusCode().is2xxSuccessful());

        // parse response
        return response.getBody();
    }

    public <T> List<T> executeGetList(String url, Class<T> responseType) {

        // execute request
        Class arrayClass = Array.newInstance(responseType, 0).getClass();
        ResponseEntity<T[]> response = restTemplate.getForEntity(url, arrayClass);

        // sanity check (the response is a meaningful value, not e.g. 500)
        assertTrue(response.getStatusCode().is2xxSuccessful());

        // parse response
        T[] bodyData = response.getBody();
        return Arrays.asList(bodyData);
    }

    public <T> HttpStatus executePost(String url, T payload){

        // prepare JSON payload data
        HttpEntity<T> request = new HttpEntity<>(payload);

        // execute request
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        // sanity check (the response is a meaningful value, not e.g. 500)
        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is4xxClientError());

        // return status code
        return response.getStatusCode();
    }
}
