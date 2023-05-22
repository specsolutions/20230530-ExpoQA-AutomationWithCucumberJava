package eu.specsolutions.bddcourse.geekpizza.controller_tests.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import eu.specsolutions.bddcourse.geekpizza.controller.AuthController;
import eu.specsolutions.bddcourse.geekpizza.controller.HomeController;
import eu.specsolutions.bddcourse.geekpizza.dto.HomePageModelDto;
import eu.specsolutions.bddcourse.geekpizza.dto.LoginRequestDto;
import eu.specsolutions.bddcourse.geekpizza.controller_tests.support.AuthContext;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeStepDefinitions {

    private HomePageModelDto homePageModelDto;

    @Autowired
    private AuthContext authContext;

    @When("the client checks the home page")
    public void theClientChecksTheHomePage() {
        HomeController controller = new HomeController();
        homePageModelDto = controller.getHomePageModel(authContext.getAuthToken(), null);
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
