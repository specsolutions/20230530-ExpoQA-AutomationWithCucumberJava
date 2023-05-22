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

    private List<MenuItem> menuItems;

    @Autowired
    private WebApiContext webApiContext;

    @When("the client checks the menu page")
    public void theClientChecksTheMenuPage() {

        menuItems = webApiContext.executeGetList("/api/menu", MenuItem.class);
    }

    @Then("there should be {int} pizzas listed")
    public void thereShouldBePizzasListed(int expectedCount) {
        assertEquals(expectedCount, menuItems.size());
    }

    @Then("the following pizzas should be listed in this order")
    public void theFollowingPizzasShouldBeListedInThisOrder(DataTable expectedMenuItemsTable) {

        DataTableComparer.assertMatchesToList(expectedMenuItemsTable, menuItems);
    }
}
