package eu.specsolutions.bddcourse.geekpizza.api_tests.step_definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import eu.specsolutions.bddcourse.geekpizza.api_tests.drivers.OrderApiDriver;
import eu.specsolutions.bddcourse.geekpizza.dto.AddToOrderRequestDto;
import eu.specsolutions.bddcourse.geekpizza.model.Order;
import eu.specsolutions.bddcourse.geekpizza.model.OrderItemSize;
import eu.specsolutions.bddcourse.geekpizza.api_tests.support.DataTableComparer;
import eu.specsolutions.bddcourse.geekpizza.api_tests.support.DomainDefaults;
import io.cucumber.datatable.DataTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class MyOrderStepDefinitions {

    private Order myOrderResponse;
    private DataTable orderedItems;

    @Autowired
    private OrderApiDriver orderApiDriver;


    @Given("the client has the following items in the basket")
    public void theClientHasTheFollowingItemsInTheBasket(DataTable orderItemsTable) {
        List<Map<String, String>> orderItems = orderItemsTable.asMaps();

        for (Map<String, String> orderItemRow: orderItems) {
            AddToOrderRequestDto addToOrderRequestDto = new AddToOrderRequestDto(
                    orderItemRow.get("name"),
                    orderItemRow.containsKey("size") ?
                            OrderItemSize.valueOf(OrderItemSize.class, orderItemRow.get("size")) :
                            DomainDefaults.orderItemSize
            );
            orderApiDriver.ensureAddToOrder(addToOrderRequestDto);
        }
        orderedItems = orderItemsTable;
    }


    @Given("the client has items in the basket")
    public void theClientHasItemsInTheBasket() {
        orderApiDriver.ensureAddToOrder(DomainDefaults.menuItemName, DomainDefaults.orderItemSize);
    }

    @When("the client checks the my order page")
    public void theClientChecksTheMyOrderPage() {

        myOrderResponse = orderApiDriver.getMyOrder();
    }

    @Then("the following items should be listed on the my order page")
    public void theFollowingItemsShouldBeListedOnTheMyOrderPage(DataTable expectedOrderItemsTable) {

        DataTableComparer.assertMatchesToList(expectedOrderItemsTable, myOrderResponse.getOrderItems());
    }

    @Then("the ordered items should be listed on the my order page")
    public void theOrderedItemsShouldBeListedOnTheMyOrderPage() {
        theFollowingItemsShouldBeListedOnTheMyOrderPage(orderedItems);
    }
}
