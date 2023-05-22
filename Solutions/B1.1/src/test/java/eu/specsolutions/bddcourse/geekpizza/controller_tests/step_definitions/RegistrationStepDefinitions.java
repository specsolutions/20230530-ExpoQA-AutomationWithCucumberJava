package eu.specsolutions.bddcourse.geekpizza.controller_tests.step_definitions;

import eu.specsolutions.bddcourse.geekpizza.controller.UserController;
import eu.specsolutions.bddcourse.geekpizza.dto.RegisterRequestDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.*;

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


    // Step definitions for the alternative approach

    private List<Map<String, String>> _registerInputTable;

    @Given("the client provides registration details as")
    public void GivenTheClientProvidesRegistrationDetailsAs(List<Map<String, String>> registerInputTable) {
        // we only "remember" the input details here, but make it modifiable
        _registerInputTable = Collections.singletonList(new HashMap<>(registerInputTable.get(0)));
    }

    @Given("the field {string} is set to {string}")
    public void GivenTheFieldIsSetTo(String field, String value) {
        if (Objects.equals(field, "passwords")) // special case to set both password fields
        {
            _registerInputTable.get(0).put("password", value);
            _registerInputTable.get(0).put("password re-enter", value);
        } else {
            _registerInputTable.get(0).put(field, value);
        }
    }

    @When("the client attempts to register")
    public void WhenTheClientAttemptsToRegister() {
        // we just need to perform the registration with the fields we built up
        WhenTheClientAttemptsToRegisterWith(_registerInputTable);
    }
}
