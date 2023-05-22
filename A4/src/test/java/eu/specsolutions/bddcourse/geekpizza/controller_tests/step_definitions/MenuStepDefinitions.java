package eu.specsolutions.bddcourse.geekpizza.controller_tests.step_definitions;

import eu.specsolutions.bddcourse.geekpizza.controller_tests.support.DataTableComparer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import eu.specsolutions.bddcourse.geekpizza.controller.MenuController;
import eu.specsolutions.bddcourse.geekpizza.model.MenuItem;
import io.cucumber.datatable.DataTable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenuStepDefinitions {

    private List<MenuItem> menuItems;

    @When("the client checks the menu page")
    public void theClientChecksTheMenuPage() {
        MenuController controller = new MenuController();
        menuItems = controller.getMenuItems();
    }

    @Then("there should be {int} pizzas listed")
    public void thereShouldBePizzasListed(int expectedCount) {
        assertEquals(expectedCount, menuItems.size());
    }

    @Then("the following pizzas should be listed in this order")
    public void theFollowingPizzasShouldBeListedInThisOrder(DataTable expectedMenuItemsTable) {

        // The assertMatchesToList helper method compares the menu item list
        // with the data table by matching the column headers with the field names.
        // The manual for-loop implementation is replaced by that.
        DataTableComparer.assertMatchesToList(expectedMenuItemsTable, menuItems);

//        assertEquals(expectedMenuItemsTable.size(), menuItems.size());
//        for (int i = 0; i < expectedMenuItemsTable.size(); i++) {
//            Map<String, String> expectedMenuItemRow = expectedMenuItemsTable.get(i);
//            MenuItem actualMenuItem = menuItems.get(i);
//
//            if (expectedMenuItemRow.containsKey("name"))
//                assertEquals(expectedMenuItemRow.get("name"), actualMenuItem.getName());
//            if (expectedMenuItemRow.containsKey("ingredients"))
//                assertEquals(expectedMenuItemRow.get("ingredients"), actualMenuItem.getIngredients());
//        }
    }
}
