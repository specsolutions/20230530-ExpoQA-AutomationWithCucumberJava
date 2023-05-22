package eu.specsolutions.bddcourse.geekpizza.controller_tests.step_definitions;

import eu.specsolutions.bddcourse.geekpizza.controller.UserController;
import eu.specsolutions.bddcourse.geekpizza.dto.RegisterRequestDto;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationStepDefinitions {
    private Exception _registerError;

    private void AttemptRegister(RegisterRequestDto inputModel) {
        try {
            _registerError = null;
            UserController controller = new UserController();
            controller.register(inputModel);
        } catch (Exception ex) {
            _registerError = ex;
        }
    }

    @When("the client attempts to register with")
    public void WhenTheClientAttemptsToRegisterWith(List<Map<String, String>> registerInputTable) {

        RegisterRequestDto inputModel = new RegisterRequestDto(
                registerInputTable.get(0).getOrDefault("user name", "Trillian"),
                registerInputTable.get(0).getOrDefault("password", "139139"),
                registerInputTable.get(0).getOrDefault("password re-enter", "139139"));
        AttemptRegister(inputModel);
    }

    @When("the client attempts to register with user name {string} and password {string}")
    public void WhenTheClientAttemptsToRegisterWithUserNameAndPassword(String userName, String password) {
        AttemptRegister(new RegisterRequestDto(userName, password, password));
    }

    @Then("the registration should be successful")
    public void ThenTheRegistrationShouldBeSuccessful() {
        assertNull(_registerError, "The registration should be successful");
    }

    @Then("the registration should fail with {string}")
    public void ThenTheRegistrationShouldFailWith(String expectedErrorMessage) {
        assertNotNull(_registerError, "The registration should fail");
        assertTrue(_registerError.getMessage().contains(expectedErrorMessage), "'" + _registerError.getMessage() + "' should contain '" + expectedErrorMessage + "'");
    }
}
