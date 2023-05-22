package eu.specsolutions.bddcourse.geekpizza.controller_tests.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import eu.specsolutions.bddcourse.geekpizza.controller.AuthController;
import eu.specsolutions.bddcourse.geekpizza.dto.LoginRequestDto;
import eu.specsolutions.bddcourse.geekpizza.controller_tests.support.AuthContext;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthStepDefinitions {

    @Autowired
    private AuthContext authContext;

    @Given("the client is logged in")
    public void theClientIsLoggedIn() {
        // The login process generates an authentication token that has to be passed in
        // for all subsequent controller method calls in the same user session.
        // You can see this in the method below, where we call the HomeController.GetHomePageModel
        // method.
        String defaultUserName = "Marvin";
        AuthController controller = new AuthController();
        String token = controller.Login(new LoginRequestDto(defaultUserName, "1234"), null);
        authContext.setAuthToken(token);
        authContext.setLoggedInUserName(defaultUserName);
    }
}
