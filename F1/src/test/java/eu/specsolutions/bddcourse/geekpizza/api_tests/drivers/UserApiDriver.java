package eu.specsolutions.bddcourse.geekpizza.api_tests.drivers;

import eu.specsolutions.bddcourse.geekpizza.dto.RegisterRequestDto;
import eu.specsolutions.bddcourse.geekpizza.api_tests.support.WebApiContext;
import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.spring.ScenarioScope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class UserApiDriver {

    @Autowired
    protected WebApiContext webApiContext;

    private HttpStatus response;

    public boolean attemptRegister(String userName, String password, String passwordReEnter){
        response = webApiContext.executePost("/api/user", new RegisterRequestDto(userName, password, passwordReEnter));
        return response == HttpStatus.OK;
    }
}
