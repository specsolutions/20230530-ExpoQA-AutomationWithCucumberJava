package eu.specsolutions.bddcourse.geekpizza.controller_tests.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import eu.specsolutions.bddcourse.geekpizza.controller.HomeController;
import eu.specsolutions.bddcourse.geekpizza.controller.MenuController;
import eu.specsolutions.bddcourse.geekpizza.dto.HomePageModelDto;
import eu.specsolutions.bddcourse.geekpizza.model.MenuItem;
import eu.specsolutions.bddcourse.geekpizza.repository.GeekPizzaRepository;

import java.util.List;

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
}
