package eu.specsolutions.bddcourse.geekpizza.controller_tests.step_definitions;

import io.cucumber.java.en.Given;
import eu.specsolutions.bddcourse.geekpizza.model.MenuItem;
import eu.specsolutions.bddcourse.geekpizza.repository.GeekPizzaRepository;

import java.util.List;
import java.util.Map;

public class AdminStepDefinitions {

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
}
