package eu.specsolutions.bddcourse.geekpizza.api_tests.step_definitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import eu.specsolutions.bddcourse.geekpizza.api_tests.drivers.OrderApiDriver;
import eu.specsolutions.bddcourse.geekpizza.model.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderDetailsStepDefinitions {

    @Autowired
    private OrderApiDriver orderApiDriver;

    @When("the client specifies {date} at {time} as delivery time")
    public void theClientSpecifiesDateAtTimeAsDeliveryTime(LocalDate date, LocalTime time) {
        orderApiDriver.updateOrderDetails(date, time);
    }

    @Then("the order should indicate that the delivery date is {date}")
    public void theOrderShouldIndicateThatTheDeliveryDateIsDate(LocalDate expectedDate) {
        Order myOrderResponse = orderApiDriver.getMyOrder();
        assertEquals(expectedDate, myOrderResponse.getDeliveryDate());
    }

    @Then("the delivery time should be {time}")
    public void theDeliveryTimeShouldBe(LocalTime expectedTime) {
        Order myOrderResponse = orderApiDriver.getMyOrder();
        assertEquals(expectedTime, myOrderResponse.getDeliveryTime());
    }
}
