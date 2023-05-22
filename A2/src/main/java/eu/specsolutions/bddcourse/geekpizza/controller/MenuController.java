package eu.specsolutions.bddcourse.geekpizza.controller;

import eu.specsolutions.bddcourse.geekpizza.model.MenuItem;
import eu.specsolutions.bddcourse.geekpizza.repository.GeekPizzaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @GetMapping
    public List<MenuItem> getMenuItems(){

        GeekPizzaRepository repository = new GeekPizzaRepository();
        return repository.getMenuItems().stream()
                .filter(mi -> !mi.getIsInactive())

                // Uncomment the next line to make the scenario in A2 pass
                //.sorted(Comparator.comparing(MenuItem::getName))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MenuItem getMenuItem(@PathVariable int id){

        GeekPizzaRepository repository = new GeekPizzaRepository();
        MenuItem menuItem = repository.getMenuItemById(id);
        if (menuItem == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "menu item not found");

        return menuItem;
    }
}
