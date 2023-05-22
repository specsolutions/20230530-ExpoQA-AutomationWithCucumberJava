package eu.specsolutions.bddcourse.geekpizza.api_tests.step_definitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import eu.specsolutions.bddcourse.geekpizza.api_tests.drivers.OrderApiDriver;
import eu.specsolutions.bddcourse.geekpizza.api_tests.support.ApiAuthContext;
import eu.specsolutions.bddcourse.geekpizza.api_tests.drivers.AuthApiDriver;
import eu.specsolutions.bddcourse.geekpizza.api_tests.support.DomainDefaults;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthStepDefinitions {

    private Boolean loginResult;

    @Autowired
    private OrderApiDriver orderApiDriver;
    @Autowired
    private AuthApiDriver authApiDriver;
    @Autowired
    private ApiAuthContext authContext;

    @Given("the client is logged in")
    @Before(value = "@login", order = 20)
    public void theClientIsLoggedIn() {
        boolean result = authApiDriver.attemptLogin(DomainDefaults.userName, DomainDefaults.password);
        assertTrue(result);
        authContext.setLoggedInUserName(DomainDefaults.userName);
    }

    @Given("the client is logged in with user name {string} and password {string}")
    public void theClientIsLoggedInWithUserNameFordAndPassword(String userName, String password) {

        boolean result = authApiDriver.attemptLogin(userName, password);
        assertTrue(result);
        authContext.setLoggedInUserName(userName);
    }

    @When("the client attempts to log in with user name {string} and password {string}")
    public void theClientAttemptsToLogInWithUserNameAndPassword(String userName, String password) {

        loginResult = authApiDriver.attemptLogin(userName, password);
    }

    @Then("the login attempt should be successful")
    public void theLoginAttemptShouldBeSuccessful() {

        assertTrue(loginResult);
    }

    @Then("the client should be able to access member-only services")
    public void theClientShouldBeAbleToAccessMemberOnlyServices() {
        // we use the "my order" api as an example of a member-only service
        orderApiDriver.getMyOrder();
    }
}
