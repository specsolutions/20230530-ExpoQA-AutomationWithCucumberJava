package eu.specsolutions.bddcourse.geekpizza.api_tests.step_definitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import eu.specsolutions.bddcourse.geekpizza.api_tests.drivers.UserApiDriver;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationStepDefinitions {

    private Boolean registerResult;

    @Autowired
    private UserApiDriver userApiDriver;

    @Given("there is a user registered with user name {string} and password {string}")
    public void thereIsAUserRegisteredWithUserNameFordAndPassword(String userName, String password) {
        boolean result = userApiDriver.attemptRegister(userName, password, password);
        assertTrue(result);
    }

    @When("the client attempts to register with user name {string} and password {string}")
    public void theClientAttemptsToRegisterWithUserNameAndPassword(String userName, String password) {

        registerResult = userApiDriver.attemptRegister(userName, password, password);
    }

    @Then("the registration should be successful")
    public void theRegistrationShouldBeSuccessful() {

        assertTrue(registerResult);
    }
}
