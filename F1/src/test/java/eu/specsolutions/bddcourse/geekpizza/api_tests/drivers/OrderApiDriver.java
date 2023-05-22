package eu.specsolutions.bddcourse.geekpizza.api_tests.drivers;

import eu.specsolutions.bddcourse.geekpizza.dto.AddToOrderRequestDto;
import eu.specsolutions.bddcourse.geekpizza.dto.OrderDetailsPageModelDto;
import eu.specsolutions.bddcourse.geekpizza.model.Order;
import eu.specsolutions.bddcourse.geekpizza.model.OrderItemSize;
import eu.specsolutions.bddcourse.geekpizza.api_tests.support.WebApiContext;
import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.spring.ScenarioScope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Component
@ScenarioScope
public class OrderApiDriver {

    @Autowired
    protected WebApiContext webApiContext;

    private HttpStatus response;

    public Order getMyOrder(){
        return webApiContext.executeGet("/api/order", Order.class);
    }

    public boolean attemptAddToOrder(AddToOrderRequestDto addToOrderRequestDto){
        response = webApiContext.executePost("/api/order", addToOrderRequestDto);
        return response == HttpStatus.OK;
    }

    public void ensureAddToOrder(String name, OrderItemSize size){
        ensureAddToOrder(new AddToOrderRequestDto(name, size));
    }

    public void ensureAddToOrder(AddToOrderRequestDto addToOrderRequestDto) {
        boolean response = attemptAddToOrder(addToOrderRequestDto);
        assertTrue(response);
    }

    public boolean updateOrderDetails(LocalDate date, LocalTime time){
        OrderDetailsPageModelDto pageModelDto = new OrderDetailsPageModelDto();
        pageModelDto.setDeliveryDate(date);
        pageModelDto.setDeliveryTime(time);
        HttpStatus response = webApiContext.executePut("/api/order", pageModelDto);
        return response == HttpStatus.OK;
    }
}
