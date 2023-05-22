package eu.specsolutions.bddcourse.geekpizza.controller;

import eu.specsolutions.bddcourse.geekpizza.dto.AddToOrderRequestDto;
import eu.specsolutions.bddcourse.geekpizza.dto.OrderDetailsPageModelDto;
import eu.specsolutions.bddcourse.geekpizza.model.MenuItem;
import eu.specsolutions.bddcourse.geekpizza.model.Order;
import eu.specsolutions.bddcourse.geekpizza.model.OrderItem;
import eu.specsolutions.bddcourse.geekpizza.model.OrderItemSize;
import eu.specsolutions.bddcourse.geekpizza.repository.GeekPizzaRepository;
import eu.specsolutions.bddcourse.geekpizza.service.AuthenticationService;
import eu.specsolutions.bddcourse.geekpizza.service.PriceCalculatorService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final PriceCalculatorService priceCalculatorService;

    public OrderController() {
        this(new PriceCalculatorService());
    }

    public OrderController(PriceCalculatorService priceCalculatorService) {
        this.priceCalculatorService = priceCalculatorService;
    }

    @GetMapping
    public Order getMyOrder(String token, HttpServletRequest request){
        String userName = AuthenticationService.ensureAuthenticated(request, token);

        GeekPizzaRepository repository = new GeekPizzaRepository();
        return repository.getMyOrder(userName);
    }

    @PutMapping
    public Order updateOrderDetails(@RequestBody OrderDetailsPageModelDto orderUpdates, String token, HttpServletRequest request) {
        String userName = AuthenticationService.ensureAuthenticated(request, token);

        GeekPizzaRepository repository = new GeekPizzaRepository();
        Order myOrder = repository.getMyOrder(userName);
        if (orderUpdates.getDeliveryStreetAddress() != null)
            myOrder.getDeliveryAddress().setStreetAddress(orderUpdates.getDeliveryStreetAddress());
        if (orderUpdates.getDeliveryCity() != null)
            myOrder.getDeliveryAddress().setCity(orderUpdates.getDeliveryCity());
        if (orderUpdates.getDeliveryZip() != null)
            myOrder.getDeliveryAddress().setZip(orderUpdates.getDeliveryZip());
        if (orderUpdates.getDeliveryDate().isPresent())
            myOrder.setDeliveryDate(orderUpdates.getDeliveryDate().get());
        if (orderUpdates.getDeliveryTime().isPresent())
            myOrder.setDeliveryTime(orderUpdates.getDeliveryTime().get());
        repository.saveChanges();

        return myOrder;
    }

    @PostMapping
    public Order addToOrder(@RequestBody AddToOrderRequestDto requestDto, String token, HttpServletRequest request) {
        String userName = AuthenticationService.ensureAuthenticated(request, token);

        GeekPizzaRepository repository = new GeekPizzaRepository();
        MenuItem menuItem = repository.findMenuItemByName(requestDto.getMenuItemName());
        Order myOrder = repository.getMyOrder(userName);
        if (menuItem != null){
            myOrder.getOrderItems().add(new OrderItem(menuItem.getName(), requestDto.getSize()));
            priceCalculatorService.updatePrice(myOrder);
            repository.saveChanges();
        }
        return myOrder;
    }

    @PatchMapping
    public void placeOrder(String token, HttpServletRequest request) {
        String userName = AuthenticationService.ensureAuthenticated(request, token);

        // we do not place an order for real, but just clear the current order
        GeekPizzaRepository repository = new GeekPizzaRepository();
        repository.deleteMyOrder(userName);
        repository.saveChanges();
    }
}
