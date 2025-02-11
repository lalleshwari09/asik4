package bibo.demo;

public class Order {
    private final int orderId;
    private final Item product;
    private final int quantity;
    private final Customer customer;

    public Order(int orderId, Item product, int quantity, Customer customer) {
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.customer = customer;
}
public int getOrderId() {
    return orderId;
}

public Item getProduct() {
    return product;
}

public int getQuantity() {
    return quantity;
}

public Customer getCustomer() {
    return customer;
}
}