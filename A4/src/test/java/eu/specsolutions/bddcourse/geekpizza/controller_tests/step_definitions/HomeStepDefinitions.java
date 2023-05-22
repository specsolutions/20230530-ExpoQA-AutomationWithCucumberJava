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

public class HomeStepDefinitions {

    private HomePageModelDto homePageModelDto;
    private String authToken;

    //TODO: Move this step definition method to AuthStepDefinitions.cs
    @Given("the client is logged in")
    public void theClientIsLoggedIn() {
        // The login process generates an authentication token that has to be passed in
        // for all subsequent controller method calls in the same user session.
        // You can see this in the method below, where we call the HomeController.GetHomePageModel
        // method.
        AuthController controller = new AuthController();
        authToken = controller.Login(new LoginRequestDto("Marvin", "1234"), null);
    }

    @When("the client checks the home page")
    public void theClientChecksTheHomePage() {
        HomeController controller = new HomeController();
        homePageModelDto = controller.getHomePageModel(authToken, null);
    }

    @Then("the home page main message should be: {string}")
    public void theHomePageMainMessageShouldBe(String expectedMessage) {

        assertEquals(expectedMessage, homePageModelDto.getWelcomeMessage());
    }

    @Then("the user name of the client should be on the home page")
    public void theUserNameOfTheClientShouldBeOnTheHomePage() {
        assertEquals("Marvin", homePageModelDto.getUserName());
    }
}
