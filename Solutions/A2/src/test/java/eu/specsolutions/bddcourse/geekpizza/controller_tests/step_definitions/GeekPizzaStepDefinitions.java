package eu.specsolutions.bddcourse.geekpizza.controller_tests.step_definitions;

import eu.specsolutions.bddcourse.geekpizza.controller_tests.support.DataTableComparer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import eu.specsolutions.bddcourse.geekpizza.controller.HomeController;
import eu.specsolutions.bddcourse.geekpizza.controller.MenuController;
import eu.specsolutions.bddcourse.geekpizza.dto.HomePageModelDto;
import eu.specsolutions.bddcourse.geekpizza.model.MenuItem;
import eu.specsolutions.bddcourse.geekpizza.repository.GeekPizzaRepository;
import io.cucumber.datatable.DataTable;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeekPizzaStepDefinitions {

    private HomePageModelDto homePageModelDto;
    private List<MenuItem> menuItems;

    @Given("the menu has been configured to contain {int} active and {int} inactive pizzas")
    public void theMenuHasBeenConfiguredToContainActiveAndInactivePizzas(int activePizzaCount, int inactivePizzaCount) {
        // We ensure the preconditions by setting the menu records directly to the database (in a pretty verbose way).
        // Alternatively we could also ensure the preconditions by using the AdminController class...

        // create a database connection
        GeekPizzaRepository repository = new GeekPizzaRepository();

        // clear menu
        repository.getMenuItems().clear();

        // add active pizzas
        for (int i = 0; i < activePizzaCount; i++) {
            MenuItem menuItem = new MenuItem("Pizza" + i, "[default ingredients]", 1000);
            repository.getMenuItems().add(menuItem);
        }

        // add inactive pizzas (ignore the code duplication for now)
        for (int i = 0; i < inactivePizzaCount; i++) {
            MenuItem menuItem = new MenuItem("Old Pizza" + i, "[default ingredients]", 1000, true);
            repository.getMenuItems().add(menuItem);
        }

        // save changed to the database
        repository.saveChanges();
    }

    @Given("the menu has been configured to contain the following pizzas")
    public void theMenuHasBeenConfiguredToContainTheFollowingPizzas(List<Map<String, String>> menuItemTable) {
        GeekPizzaRepository repository = new GeekPizzaRepository();
        repository.getMenuItems().clear();
        for (int i = 0; i < menuItemTable.size(); i++) {
            MenuItem menuItem = new MenuItem(
                    menuItemTable.get(i).get("name"),
                    menuItemTable.get(i).get("ingredients"),
                    Integer.parseInt(menuItemTable.get(i).get("calories")),
                    Boolean.parseBoolean(menuItemTable.get(i).get("inactive")));
            repository.getMenuItems().add(menuItem);
        }
        repository.saveChanges();
    }

    @When("the client checks the home page")
    public void theClientChecksTheHomePage() {
        HomeController controller = new HomeController();
        homePageModelDto = controller.getHomePageModel();
    }

    @Then("the home page main message should be: {string}")
    public void theHomePageMainMessageShouldBe(String expectedMessage) {

        assertEquals(expectedMessage, homePageModelDto.getWelcomeMessage());
    }

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
