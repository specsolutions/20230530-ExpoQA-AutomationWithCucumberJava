package eu.specsolutions.bddcourse.geekpizza.api_tests.support;

import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WebApiHooks {

    @Autowired
    private WebApiContext webApiContext;

    @Before("@webapi")
    public void InitWebApi(){
        // initialization moved to WebApiContext constructor
        // here we just make sure that the WebApiContext was initialized
        assertNotNull(webApiContext);
    }
}
