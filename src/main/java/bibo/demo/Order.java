package bibo.demo;

public class Order {
    private int orderId;
    private Item product;
    private int quantity;
    private Customer customer;

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