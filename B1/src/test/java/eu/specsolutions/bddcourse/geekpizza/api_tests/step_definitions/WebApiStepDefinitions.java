package eu.specsolutions.bddcourse.geekpizza.api_tests.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import eu.specsolutions.bddcourse.geekpizza.dto.HomePageModelDto;
import eu.specsolutions.bddcourse.geekpizza.dto.LoginRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.CookieHandler;
import java.net.CookieManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebApiStepDefinitions {

    private HomePageModelDto homePageModelDto;

    // get a RestTemplate that applies the request on the started app instance
    @Autowired
    private TestRestTemplate restTemplate;

    @Given("the client is logged in")
    public void theClientIsLoggedIn() {
        // initialize cookie manager to be able to manage user login sessions
        CookieHandler.setDefault(new CookieManager());

        // prepare JSON payload data
        HttpEntity<LoginRequestDto> request = new HttpEntity<>(new LoginRequestDto("Marvin", "1234"));
        // execute request
        ResponseEntity<String> response = restTemplate.postForEntity("/api/auth", request, String.class);
        // sanity check (the response is a meaningful value, not e.g. 500)
        assertTrue(response.getStatusCode().is2xxSuccessful() || response.getStatusCode().is4xxClientError());
        // functional check
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @When("the client checks the home page")
    public void theClientChecksTheHomePage() {
        // execute request
        ResponseEntity<HomePageModelDto> response = restTemplate.getForEntity("/api/home", HomePageModelDto.class);
        // sanity check (the response is a meaningful value, not e.g. 500)
        assertTrue(response.getStatusCode().is2xxSuccessful());
        // save result for assertions in Then steps
        homePageModelDto = response.getBody();
    }

    @Then("the user name of the client should be on the home page")
    public void theUserNameOfTheClientShouldBeOnTheHomePage() {
        // functional check
        assertEquals("Marvin", homePageModelDto.getUserName());
    }
}
