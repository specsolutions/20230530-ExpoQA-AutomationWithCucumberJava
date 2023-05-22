package eu.specsolutions.bddcourse.geekpizza.api_tests.support;

import io.cucumber.java.Before;
import eu.specsolutions.bddcourse.geekpizza.model.MenuItem;
import eu.specsolutions.bddcourse.geekpizza.model.User;
import eu.specsolutions.bddcourse.geekpizza.repository.GeekPizzaRepository;

import java.util.Arrays;

public class DatabaseHooks {

    @Before(order = 10)
    public void resetDatabaseToBaseline(){

        GeekPizzaRepository repository = new GeekPizzaRepository();
        repository.clearData();
        repository.saveChanges();

        DomainDefaults.addDefaultPizzas();
        DomainDefaults.addDefaultUsers();
    }


}
