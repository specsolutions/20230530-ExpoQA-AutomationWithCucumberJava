package eu.specsolutions.bddcourse.geekpizza.api_tests.step_definitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import eu.specsolutions.bddcourse.geekpizza.model.MenuItem;
import eu.specsolutions.bddcourse.geekpizza.api_tests.support.DataTableComparer;
import eu.specsolutions.bddcourse.geekpizza.api_tests.support.WebApiContext;
import io.cucumber.datatable.DataTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenuStepDefinitions {

    @Autowired
    private WebApiContext webApiContext;

    //TODO: add step definitions
    // In order to retrieve the menu list, invoke
    //     webApiContext.executeGetList("/api/menu", MenuItem.class)
}
