package eu.specsolutions.bddcourse.geekpizza.api_tests.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import eu.specsolutions.bddcourse.geekpizza.dto.HomePageModelDto;
import eu.specsolutions.bddcourse.geekpizza.api_tests.support.WebApiContext;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeStepDefinitions {

    private HomePageModelDto homePageModelDto;

    @Autowired
    private WebApiContext webApiContext;

    @When("the client checks the home page")
    public void theClientChecksTheHomePage() {

        homePageModelDto = webApiContext.executeGet("/api/home", HomePageModelDto.class);
    }

    @Then("the user name of the client should be on the home page")
    public void theUserNameOfTheClientShouldBeOnTheHomePage() {

        assertEquals("Marvin", homePageModelDto.getUserName());
    }
}
