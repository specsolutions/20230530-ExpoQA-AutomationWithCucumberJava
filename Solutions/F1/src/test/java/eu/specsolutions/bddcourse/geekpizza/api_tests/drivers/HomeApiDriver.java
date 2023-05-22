package eu.specsolutions.bddcourse.geekpizza.api_tests.drivers;

import eu.specsolutions.bddcourse.geekpizza.dto.HomePageModelDto;
import eu.specsolutions.bddcourse.geekpizza.api_tests.support.WebApiContext;
import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class HomeApiDriver {

    @Autowired
    protected WebApiContext webApiContext;

    public HomePageModelDto getHomePageModel(){
        return webApiContext.executeGet("/api/home", HomePageModelDto.class);
    }
}
