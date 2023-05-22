package eu.specsolutions.bddcourse.geekpizza.service;

import eu.specsolutions.bddcourse.geekpizza.model.Order;
import eu.specsolutions.bddcourse.geekpizza.model.OrderItem;
import eu.specsolutions.bddcourse.geekpizza.model.OrderPrice;

public class PriceCalculatorService {
    private static final double LARGE_PRICE = 25;
    private static final double MEDIUM_PRICE = 15;
    private static final double SMALL_PRICE = 10;
    private static final double DELIVERY_COST = 5;
    private static final double DELIVERY_COST_THRESHOLD = 40;

    protected double getOrderItemPrice(OrderItem orderItem)
    {
        switch (orderItem.getSize())
        {
            case Large:
                return LARGE_PRICE;
            case Small:
                return SMALL_PRICE;
            default:
                return MEDIUM_PRICE;
        }
    }

    protected double getSubtotal(Order order){
        double subtotal = 0;
        subtotal += order.getOrderItems().stream().mapToDouble(this::getOrderItemPrice).sum();
        return subtotal;
    }

    protected double getDeliveryCost(double subtotal){
        double deliveryCosts = 0;
        if (subtotal <= DELIVERY_COST_THRESHOLD)
            deliveryCosts = DELIVERY_COST;
        return deliveryCosts;
    }

    protected OrderPrice getOrderPrice(Order order)
    {
        double subtotal = getSubtotal(order);
        double deliveryCosts = getDeliveryCost(subtotal);
        return new OrderPrice(subtotal, deliveryCosts, subtotal + deliveryCosts);
    }

    public void updatePrice(Order order)
    {
        order.setPrices(getOrderPrice(order));
    }
}
