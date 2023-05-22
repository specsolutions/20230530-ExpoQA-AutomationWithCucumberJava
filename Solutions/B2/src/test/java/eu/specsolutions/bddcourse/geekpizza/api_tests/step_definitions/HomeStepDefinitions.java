package eu.specsolutions.bddcourse.geekpizza.api_tests.step_definitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import eu.specsolutions.bddcourse.geekpizza.dto.HomePageModelDto;
import eu.specsolutions.bddcourse.geekpizza.api_tests.support.ApiAuthContext;
import eu.specsolutions.bddcourse.geekpizza.api_tests.support.WebApiContext;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeStepDefinitions {

    private HomePageModelDto homePageModelDto;

    @Autowired
    private WebApiContext webApiContext;
    @Autowired
    private ApiAuthContext authContext;

    @When("the client checks the home page")
    public void theClientChecksTheHomePage() {

        homePageModelDto = webApiContext.executeGet("/api/home", HomePageModelDto.class);
    }

    @Then("the home page main message should be: {string}")
    public void theHomePageMainMessageShouldBe(String expectedMessage) {

        assertEquals(expectedMessage, homePageModelDto.getWelcomeMessage());
    }

    @Then("the user name of the client should be on the home page")
    public void theUserNameOfTheClientShouldBeOnTheHomePage() {

        assertTrue(authContext.isLoggedIn());
        assertEquals(authContext.getLoggedInUserName(), homePageModelDto.getUserName());
    }
}
