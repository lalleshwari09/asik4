package bibo.demo;

public class Item {
    private final int productId;
    private final String name;
    private final double price;

    public Item(int productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;  
}
public int getProductId() {
    return productId;
}

public String getName() {
    return name;
}

public double getPrice() {
    return price;
}
}