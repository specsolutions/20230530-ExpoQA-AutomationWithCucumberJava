package eu.specsolutions.bddcourse.geekpizza.api_tests.drivers;

import eu.specsolutions.bddcourse.geekpizza.dto.LoginRequestDto;
import eu.specsolutions.bddcourse.geekpizza.api_tests.support.WebApiContext;
import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.spring.ScenarioScope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class AuthApiDriver {

    @Autowired
    protected WebApiContext webApiContext;

    private HttpStatus response;

    public boolean attemptLogin(String userName, String password){
        response = webApiContext.executePost("/api/auth", new LoginRequestDto(userName, password));
        return response == HttpStatus.OK;
    }
}
