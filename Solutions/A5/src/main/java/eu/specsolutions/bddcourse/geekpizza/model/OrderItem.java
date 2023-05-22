package eu.specsolutions.bddcourse.geekpizza.model;

public class OrderItem {
    private String name;
    private OrderItemSize size;

    public OrderItem() { }

    public OrderItem(String name, OrderItemSize size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public OrderItemSize getSize() {
        return size;
    }
}
