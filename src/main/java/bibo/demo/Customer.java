package bibo.demo;

public class Customer {
    private final int customerId;
    private final String name;
    private final long number;

    public Customer(int customerId, String name, long number) {
        this.customerId = customerId;
        this.name = name;
        this.number = number;
}
public int getCustomerId() {
    return customerId;
}

public String getName() {
    return name;
}

public long getNumber() {
    return number;
}
}