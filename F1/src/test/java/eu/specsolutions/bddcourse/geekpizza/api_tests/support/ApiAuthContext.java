package eu.specsolutions.bddcourse.geekpizza.api_tests.support;

import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class ApiAuthContext {
    private String authToken;
    private String loggedInUserName;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setLoggedInUserName(String userName) {
        loggedInUserName = userName;
    }

    public String getLoggedInUserName() {
        return loggedInUserName;
    }

    public boolean isLoggedIn() {
        return loggedInUserName != null;
    }
}
