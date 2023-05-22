package eu.specsolutions.bddcourse.geekpizza.api_tests.drivers;

import eu.specsolutions.bddcourse.geekpizza.model.MenuItem;
import eu.specsolutions.bddcourse.geekpizza.api_tests.support.WebApiContext;
import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ScenarioScope
public class MenuApiDriver {

    @Autowired
    protected WebApiContext webApiContext;

    public List<MenuItem> getMenuItems() {
        return webApiContext.executeGetList("/api/menu", MenuItem.class);
    }
}
